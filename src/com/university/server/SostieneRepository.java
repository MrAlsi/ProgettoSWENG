package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Serializer.SerializerSostiene;
import com.university.client.model.Sostiene;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class SostieneRepository extends RemoteServiceServlet implements RepositoryDoubleInt<Sostiene> {

    DB db;
    HTreeMap<Integer, Sostiene> map;
    ServletContext context;
    private DB getDb() {

        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("sostieneDb");
            if (db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\sostiene").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("sostieneDb", db);
            }
            return db;
        }
    }

    private void createOrOpenDB() {
        this.db = getDb();
        this.map = this.db.hashMap("sostieneMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerSostiene()).createOrOpen();
    }

    @Override
    public Sostiene GetById(int id) {
        return null;
    }

    @Override
    public Sostiene[] getArrayById(int id) {
        return new Sostiene[0];
    }

    @Override
    public Sostiene[] getAll() {
        try {
            createOrOpenDB();
            Sostiene[] sostenuti = new Sostiene[map.size()];
            int j = 0;
            for (int i : map.getKeys()) {
                sostenuti[j] = map.get(i);
                j++;
            }
            return sostenuti;
        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
    }

    @Override
    public boolean Create(Sostiene object) {
        try {
            createOrOpenDB();
            map.put((map.size() + 1), object);
            db.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return false;
        }
    }

    @Override
    public boolean Remove(int id, int id2) {
        try {
            createOrOpenDB();
            for (int i : map.getKeys()) {
                if (map.get(i).getCodEsame() == id && map.get(i).getMatricola() == id2) {
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Err: accetta voto: " + e);
        }
        return false;
    }
}
