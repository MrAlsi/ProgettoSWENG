package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.services.CorsoService;
import com.university.client.model.Corso;
import com.university.client.model.Serializer.SerializerCorso;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

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

    @Override
    public int getNumeroCorsi() {
        createOrOpenDB();
        return map.size();
    }

    @Override
    public Boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente, int esame) {
        return null;
    }

    @Override
    public Corso[] getCorsi() {
        return new Corso[0];
    }

    @Override
    public Corso getCorso(String nome) {
        return null;
    }

    @Override
    public Corso[] getMieiCorsi(int matricola) {
        return new Corso[0];
    }

    @Override
    public Corso[] getCorsiDocente(int docente) {
        return new Corso[0];
    }

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

    @Override
    public boolean modificaCorso(String nomeCodice, String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente, int esame) {
        try{
            createOrOpenDB();
            Corso corso = new Corso(nome, dataInizio, dataFine, descrizione, codocente, docente, esame);
            for(int i : map.getKeys()){
                if(map.get(i).getNome() == nomeCodice){
                    map.replace(i, corso);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: mdofica studente " + e);
        }
        return false;
    }

    @Override
    public boolean aggiungiEsame(String nome, int esame) {
        return false;
    }

    @Override
    public boolean eliminaEsame(String nome) {
        return false;
    }

    @Override
    public boolean aggiungiCodocente(String nome, int codocente) {
        return false;
    }
}
