package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Frequenta;
import com.university.client.services.CorsoService;
import com.university.client.model.Corso;
import com.university.client.model.Serializer.SerializerCorso;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;

public class CorsoImpl extends RemoteServiceServlet  implements CorsoService {
    DB db;
    HTreeMap<Integer, Corso> map;

    private DB getDb(){
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
    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("corsiMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerCorso()).createOrOpen();
    }

    //restituisce il numero di corsi
    @Override
    public int getNumeroCorsi() {
        createOrOpenDB();
        return map.size();
    }

    //crea un nuovo corso
    @Override
    public Boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente) {
        try{
            createOrOpenDB();
            map.put(map.size() + 1,
                    new Corso( nome, pulisciData(dataInizio), pulisciData(dataFine), descrizione, codocente, docente, -1));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    public String pulisciData(String data){
        if(data.contains(" ")) {
            String[] date = data.split(" ");
            return date[2] + " " + date[1] + " " + date[5];
        }
        return data;
    }

    //restituisce tutti i corsi
    @Override
    public Corso[] getCorsi() {
        try{
            createOrOpenDB();
            Corso[] corsi = new Corso[map.size()];
            int j = 0;
            for( int i: map.getKeys()){
                corsi[j] = map.get(i);
                j++;
            }
            return corsi;
        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    //restituisce un solo corso
    @Override
    public Corso getCorso(String nome) {
        createOrOpenDB();
        for (int id : map.getKeys()) {
            if (map.get(id).getNome().equals(nome)) {
                return map.get(id);
            }
        }
        return null;
    }


    //restituisce i corsi di un docente
    @Override
    public Corso[] getCorsiDocente(int docente) {
        try{
            createOrOpenDB();
            ArrayList<Corso> corsi=new ArrayList<>();
            Corso[] tuttiCorsi= getCorsi();
            for(Corso corso: tuttiCorsi){
                if(corso.getDocente()==docente){
                    corsi.add(corso);
                }
            }
            return corsi.toArray(new Corso[0]);
        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }


    //elimina un corso
    @Override
    public boolean eliminaCorso(String nome) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getNome().equals(nome)){
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: Elimina corso " + e);
        }
        return false;
    }


    //questo metodo è da utilizzare anche per aggiungere/eliminare un esame e un codocente
    @Override
    public boolean modificaCorso(String nomeCodice, String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente, int esame) {
        try{
            createOrOpenDB();
            Corso corso = new Corso(nome, pulisciData(dataInizio), pulisciData(dataFine), descrizione, codocente, docente, esame);
            for(int i : map.getKeys()){
                if(map.get(i).getNome().equals(nomeCodice)){
                    map.replace(i, corso);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: modifica studente " + e);
        }
        return false;
    }

}
