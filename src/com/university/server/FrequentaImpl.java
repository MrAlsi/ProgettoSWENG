package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Frequenta;
import com.university.client.model.Serializer.SerializerCorso;
import com.university.client.model.Serializer.SerializerFrequenta;
import com.university.client.services.FrequentaService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FrequentaImpl extends RemoteServiceServlet implements FrequentaService {

    DB db;
    HTreeMap<Integer, Frequenta> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("frequentaDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\frequenta").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("frequentaDb", db);
            }
            return db;
        }
    }

    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("frequentaMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerFrequenta()).createOrOpen();
    }


    //chiamata al db corsi
    private DB getCorsiDB(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("corsiDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\corso").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("corsiDb", db);
            }
            return db;
        }
    }

    //metodo che mi permette di avere qui in frequenza anche il db dei corsi
    private Corso[] traduciCorso(){
        try{
            DB dbCorsi=getCorsiDB();
            HTreeMap<Integer, Corso> mapCorsi = dbCorsi.hashMap("corsiMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerCorso()).createOrOpen();
            Corso[] corsi = new Corso [mapCorsi.size()];
            int j=0;
            for(int i: mapCorsi.getKeys()){
               corsi[j]=mapCorsi.get(i);
               j++;
            }
            return corsi;
        }catch (Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }


    //metodo per prendere tutte le istanze di frequenta
    @Override
    public Frequenta[] getFrequenta() {
        try{
            createOrOpenDB();
            Frequenta[] frequenta = new Frequenta[map.size()];
            int j = 0;
            for( int i: map.getKeys()){
                frequenta[j] = map.get(i);
                j++;
            }
            return frequenta;

        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    //metodo che mostra i corsi a cui lo studente non è ancora iscritto
    @Override
    public Corso[] getCorsiDisponibili(int matricola) {
        try {
            createOrOpenDB();
            //prendo tutti i corsi
            Corso[] tuttiCorsi = traduciCorso();
            ArrayList<Corso> corsiDisponibili = new ArrayList<>();
            HashMap<String, Corso> corsi = new HashMap<>();
            for (Corso corso : tuttiCorsi) {
                corsi.put(corso.nome, corso);
            }
            //prendo i corsi a cui sono iscritto
            ArrayList<Frequenta> mieiCorsi = getMieiCorsi(matricola);
            if(mieiCorsi.size()!=0) {
                for (Frequenta frequenta : mieiCorsi) {
                    if (!corsi.containsKey(frequenta.nomeCorso)) {
                        corsiDisponibili.add(corsi.get(frequenta.nomeCorso));
                    }
                }
                return corsiDisponibili.toArray(new Corso[0]);
            }else{
                return tuttiCorsi;
            }
        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
    }

    //metodo per prendere i miei corsi
    @Override
    public ArrayList<Frequenta> getMieiCorsi(int matricola) {
        try {
            createOrOpenDB();
            Frequenta[] frequenta = getFrequenta();
            ArrayList<Frequenta> mieiCorsi = new ArrayList<>();
            for(Frequenta corso: frequenta) {
                if (corso.matricola == matricola) {
                    mieiCorsi.add(corso);
                }
            }
            return mieiCorsi;

        }catch (Exception e){
            System.out.println("Errore: " + e);
            return null;
        }
    }

    //metodo per vedere gli studenti iscritti a un corso
    @Override
    public ArrayList<Frequenta> getStudentiIscritti(String nomeCorso) {
        try {
            createOrOpenDB();
            Frequenta[] frequenta = getFrequenta();
            ArrayList<Frequenta> studenti = new ArrayList<>();
            for(Frequenta studente: frequenta) {
                if (studente.nomeCorso.equals(nomeCorso)) {
                    studenti.add(studente);
                }
            }
            return studenti;
        }catch (Exception e){
            System.out.println("Errore: " + e);
            return null;
        }

    }

    //metodo per creare una nuova istanza di frequenta, al momento non sono ancora implementati i controlli
    @Override
    public boolean iscrivi(int matricola, String nomeCorso) {
        try{
            createOrOpenDB();
            map.put((map.size() + 1),
                    new Frequenta( matricola, nomeCorso));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    @Override
    public boolean cancellaIscrizione(int matricola, String nomeCorso) {
        try{
            createOrOpenDB();
            for(int i:map.getKeys()){
                if(map.get(i).getMatricola() == matricola && map.get(i).getNomeCorso().equals(nomeCorso)){
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }

        }catch(Exception e){
            System.out.println("Err: elimina iscrizione " + e);
        }
        return false;
    }
}
