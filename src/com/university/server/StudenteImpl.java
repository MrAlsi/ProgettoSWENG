package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.services.StudenteService;
import com.university.client.model.Frequenta;
import com.university.client.model.Serializer.SerializerFrequenta;
import com.university.client.model.Serializer.SerializerSostiene;
import com.university.client.model.Sostiene;
import com.university.client.model.Studente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class StudenteImpl extends RemoteServiceServlet implements StudenteService {
    /**
     * Restituisce tutte le informazioni personale di uno studente
     */
    @Override
    public String[] getInformazioniPersonali(Studente studente) {
        try {
            return new String[]{studente.getMail(), studente.getNome(), studente.getCognome(), String.valueOf(studente.getMatricola()), studente.getDataNascita()};
        } catch(Exception e){
            return null;
        }
    }

    /**
     * Restituisce tutti i corsi a cui non è iscritta la matricola
     */
    @Override
    public ArrayList<Frequenta> getCorsiDisponibili(int matricola) {
        Frequenta[] corsi = getCorsi();
        ArrayList<Frequenta> corsiDisponibili = new ArrayList<>();
        for(Frequenta c : corsi){
            if(c.getMatricola() != matricola){
                corsiDisponibili.add(c);
            }
        }
        return corsiDisponibili;
    }

    @Override
    public boolean iscrizioneCorso(String mail) {
        return false;
    }

    @Override
    public ArrayList<Sostiene> getVoti(int matricola) {
        ArrayList<Sostiene> esamiSvolti = new ArrayList<>();
        Sostiene[] sostenuti = caricaEsami();
        assert sostenuti != null;
        for(Sostiene s : sostenuti){
            if(s.matricola == matricola){
                esamiSvolti.add(s);
            }
        }

        return esamiSvolti;
    }

    private Sostiene[] caricaEsami() {
        try {
            DB db = getDb("C:\\MapDB\\sostiene");
            HTreeMap<String, Sostiene> map = db.hashMap("sostieneMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerSostiene()).createOrOpen();
            Sostiene[] sostiene = new Sostiene[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                sostiene[j] = map.get(i);
                j++;
            }
            return sostiene;
        } catch(Exception e){
            return null;
        }
    }

    private Frequenta[] getCorsi(){
        try {
            DB db = getDb("C:\\MapDB\\frequenta");
            HTreeMap<String, Frequenta> map = db.hashMap("frequentaMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerFrequenta()).createOrOpen();
            Frequenta[] frequenta = new Frequenta[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                frequenta[j] = map.get(i);
                j++;
            }
            return frequenta;
        } catch(Exception e){
            return null;
        }
    }
    private DB getDb(String nomeDB) {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB");
            if(db == null){
                System.out.println("Sono qui");
                db = DBMaker.fileDB(nomeDB).closeOnJvmShutdown().make();
                context.setAttribute("DB", db);
            }
            return db;
        }
    }
}
