package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.UniversityService;
import com.university.client.model.Corso;
import com.university.client.model.Docente;
import com.university.client.model.Serializer.SerializerCorso;
import com.university.client.model.Serializer.SerializerDocente;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.model.Studente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class UniversityServiceImpl extends RemoteServiceServlet implements UniversityService {
    @Override
    public String getMessage(String msg) {
        return null;
    }
    // Implementation of sample interface method

    @Override
    public int[] getDataHP(){
        return new int[]{getNumeroCorsi(), getNumeroDocenti(), getNumeroStudenti()};
    }

    public int getNumeroCorsi(){
        try{
            DB db = getDb("C:\\MapDB\\corsi.db");
            HTreeMap<String, Corso> map = db.hashMap("corsiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerCorso()).createOrOpen();
            return map.size();
        } catch(Exception e){
            return -1;
        }
    }

    public int getNumeroDocenti(){
        try{
            DB db = getDb("C:\\MapDB\\docenti.db");
            HTreeMap<String, Docente> map = db.hashMap("docentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerDocente()).createOrOpen();
            return map.size();
        } catch(Exception e){
            return -1;
        }
    }

    public int getNumeroStudenti(){
        try{
            DB db = getDb("C:\\MapDB\\studente.db");
            HTreeMap<String, Studente> map = db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerStudente()).createOrOpen();
            return map.size();
        } catch(Exception e){
            return -1;
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