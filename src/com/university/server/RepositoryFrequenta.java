package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Frequenta;
import com.university.client.model.Serializer.SerializerCorso;
import com.university.client.model.Serializer.SerializerFrequenta;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.model.Sostiene;
import com.university.client.model.Studente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;

public class RepositoryFrequenta extends RemoteServiceServlet implements Repository<Frequenta> {

    DB db;
    HTreeMap<Integer, Frequenta> map;
    ServletContext context;


    public RepositoryFrequenta(ServletContext servletContext){
        context = servletContext;
    }
    private DB getDb(){
        try{
            synchronized (context) {
                DB db = (DB)context.getAttribute("frequentaDb");
                if(db == null) {
                    db = DBMaker.fileDB("C:\\MapDB\\frequenta").closeOnJvmShutdown().checksumHeaderBypass().make();
                    context.setAttribute("frequentaDb", db);
                }
                return db;
            }
        } catch(Exception e){
            System.out.println("-----Errore: " + e);
            return null;
        }
    }

    protected void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("frequentaMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerFrequenta()).createOrOpen();
    }

    @Override
    public Frequenta GetById(int id) {
        return null;
    }

    @Override
    public Frequenta GetByString(String stringa) {
        return null;
    }

    @Override
    public Frequenta[] getAll() {
        return new Frequenta[0];
    }

    @Override
    public boolean Create(Frequenta object) {
        return false;
    }

    @Override
    public boolean Remove(int id) {
        return false;
    }

    @Override
    public boolean RemoveByString(String stringa) {
        return false;
    }

    @Override
    public boolean Update(Frequenta object) {
        return false;
    }

    @Override
    public boolean UpdateByString(Frequenta object, String stringa) {
        return false;
    }
}
