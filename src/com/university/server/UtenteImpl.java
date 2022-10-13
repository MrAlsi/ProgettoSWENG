package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Utente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class UtenteImpl extends RemoteServiceServlet {
    DB db;
    HTreeMap<String, Utente> map;

    public Utente login(String username, String password) {
        createOrOpenDB();
        for (String i : map.getKeys()) {
            if(map.get(i).getMail().equals(username) & map.get(i).getPassword().equals(password)){
                return map.get(i);
            }
        }
        return null;

    }

    private void createOrOpenDB(){
        this.db = getDb("utenti.db");
        this.map = this.db.hashMap("utentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerUtente()).createOrOpen();
    }

    private DB getDb(String nameDB){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB");
            if(db == null) {
                db = DBMaker.fileDB(nameDB).make();
                context.setAttribute("DB", db);
            }
            return db;
        }
    }
}
