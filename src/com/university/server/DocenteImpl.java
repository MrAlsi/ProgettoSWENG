package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.DocenteService;
import com.university.client.model.Corso;
import com.university.client.model.Docente;
import com.university.client.model.Serializer.SerializerCorso;
import com.university.client.model.Serializer.SerializerDocente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class DocenteImpl  extends RemoteServiceServlet implements DocenteService {
    @Override
    public Docente getInfoPersonali(int codDocente) {
        try{
            DB db = getDb("C:\\MapDB\\docenti");
            HTreeMap<String,Docente> map= db.hashMap("docentimap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerDocente()).createOrOpen();
            for(Docente d: map.getValues()){
                if(d.codDocente==codDocente){
                    return d;
                }
            }
            return null;
        }catch (Exception e){
            System.out.println("errore getinfodoc: "+e);
            return null;
        }
    }

    public boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame) {
        try{
            DB db = getDb("C:\\MapDB\\corsi");
            HTreeMap<String, Corso> map = db.hashMap("corsoMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerCorso()).createOrOpen();
            map.put(String.valueOf(map.size() + 1),
                    new Corso( nome, dataInizio, dataFine, descrizione, coDocente, docente, esame));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
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
}
