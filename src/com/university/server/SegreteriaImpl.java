package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Segreteria;
import com.university.client.model.Serializer.SerializerSegreteria;
import com.university.client.services.SegreteriaService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class SegreteriaImpl extends RemoteServiceServlet implements SegreteriaService {
    DB db;
    HTreeMap<Integer, Segreteria> map;

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
        this.map = this.db.hashMap("segretariMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerSegreteria()).createOrOpen();
    }
    @Override
    public boolean creaSegretaria(String nome, String cognome, String password) {
        try{
            createOrOpenDB();
            map.put((map.size() + 1),
                    new Segreteria(nome, cognome, creaMailSegreteria(nome, cognome), password));
            db.commit();
            return true;
        } catch(Exception e){
            System.out.println("C: AdminImpl  M: creaSegreteria: " + e);
            return false;
        }
    }
    public String creaMailSegreteria(String nome, String cognome){
        try {
            int num = 0;
            for (int i : map.getKeys()) {
                if (map.get(i).getNome() == nome && map.get(i).getCognome() == cognome) {
                    num++;
                }
            }
            if (num > 0) {
                return nome + "." + cognome + num + "@segreteria.university.com";
            } else {
                return nome + "." + cognome + "@segreteria.university.com";
            }
        } catch(Exception e){
            System.out.println("Err: creaMailSegreteria  " + e);
            return null;
        }
    }

    @Override
    public Segreteria[] getSegreteria(){
        return null;
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
