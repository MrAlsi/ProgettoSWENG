package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Segreteria;
import com.university.client.model.Serializer.SerializerSegreteria;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.model.Studente;
import com.university.client.services.SegreteriaService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class SegreteriaImpl extends RemoteServiceServlet implements SegreteriaService {
    DB db;
    HTreeMap<String, Segreteria> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("segreteriaDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\segreteria").make();
                context.setAttribute("studentiDb", db);
            }
            return db;
        }
    }
    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("segretariMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerSegreteria()).createOrOpen();
    }
    @Override
    public boolean creaSegretaria(String nome, String cognome, String password) {
        return false;
    }

    @Override
    public boolean modificaSegreteria(String nome, String cognome, String mail, String password) {
        return false;
    }

    @Override
    public boolean eliminaSegreteria(String mail) {
        return false;
    }

    @Override
    public Segreteria getInformazioni(String mail) {
        return null;
    }
}
