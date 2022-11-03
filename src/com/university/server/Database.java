package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.*;
import com.university.client.model.Serializer.SerializerEsame;
import com.university.client.services.DatabaseService;
import com.university.client.model.Serializer.SerializerStudente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class Database extends RemoteServiceServlet implements DatabaseService {
    @Override
    public void creaDB() {

    }
    private DB getDb(String nomeDB) {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB");
            if(db == null){
                db = DBMaker.fileDB(nomeDB).closeOnJvmShutdown().make();
                context.setAttribute("DB", db);
            }
            return db;
        }
    }

    @Override
    public Studente[] getStudenti() {
        try{
            DB db = getDb("C:\\MapDB\\studenti");
            HTreeMap<String, Studente> map = db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerStudente()).createOrOpen();
            Studente[] studenti = new Studente[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                studenti[j] = map.get(i);
                j++;
            }
            return studenti;

        } catch(Exception e){
            return null;
        }
    }

    @Override
    public Docente[] getDocenti() {
        return new Docente[0];
    }

    @Override
    public Segreteria[] getSegretari() {
        return new Segreteria[0];
    }

    @Override
    public Corso[] getCorsi() {
        return new Corso[0];
    }

    @Override
    public Esame[] getEsami() {
        try{
            DB db = getDb("C:\\MapDB\\esami");
            HTreeMap<String, Esame> map = db.hashMap("esamiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerEsame()).createOrOpen();
            Esame[] esami = new Esame[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                esami[j] = map.get(i);
                j++;
            }
            return esami;

        } catch(Exception e){
            return null;
        }
    }


}
