package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Docente;
import com.university.client.model.Serializer.SerializerDocente;
import com.university.client.model.Serializer.SerializerSostiene;
import com.university.client.model.Sostiene;
import com.university.client.services.SostieneService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class SostieneImpl extends RemoteServiceServlet implements SostieneService {

    DB db;
    HTreeMap<String, Sostiene> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("sostieneDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\sostiene").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("sostieneDb", db);
            }
            return db;
        }
    }

    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("sostieneMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerSostiene()).createOrOpen();
    }

    @Override
    public Sostiene[] getSostiene() {
        return new Sostiene[0];
    }

    @Override
    public ArrayList<Sostiene> getSostieneStudente(int matricola) {
        return null;
    }

    @Override
    public ArrayList<Sostiene> getStudenti(int codEsame) {
        return null;
    }

    @Override
    public boolean creaSostiene(int matricola, int codEsame, int voto) {
        return false;
    }
}
