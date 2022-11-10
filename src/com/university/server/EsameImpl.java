package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Esame;
import com.university.client.model.Frequenta;
import com.university.client.model.Serializer.SerializerEsame;
import com.university.client.model.Serializer.SerializerFrequenta;
import com.university.client.services.EsameService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class EsameImpl extends RemoteServiceServlet implements EsameService {
    DB db;
    HTreeMap<String, Esame> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("esameDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\esame").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("esameDb", db);
            }
            return db;
        }
    }

    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("esameMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerEsame()).createOrOpen();
    }

    @Override
    public boolean creaEsame(String nomeCorso, String data, String ora, String durata, String aula) {
        return false;
    }

    @Override
    public boolean modificaEsame(int codEsame, String nomeCorso, String data, String ora, String durata, String aula) {
        return false;
    }

    @Override
    public boolean eliminaEsame(int codEsame) {
        return false;
    }

    @Override
    public Esame getEsame(int codEsame) {
        return null;
    }

    @Override
    public Esame[] getEsami() {
        return new Esame[0];
    }
}
