package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.UtenteService;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.model.Studente;
import com.university.client.model.Utente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.mapdb.serializer.SerializerArray;

import javax.servlet.ServletContext;
import java.io.File;

public class UtenteImpl extends RemoteServiceServlet implements UtenteService {
    DB db;
    HTreeMap<String, Studente> map;

    public Utente login(String username, String password) {
        //createOrOpenDB();
        for (String i : map.getKeys()) {
            if(map.get(i).getMail().equals(username) & map.get(i).getPassword().equals(password)){
                return map.get(i);
            }
        }
        return null;

    }

    @Override
    public void creaUtente(String mail, String password) throws IllegalArgumentException {
        System.out.println("YAHooooo" + password);
        Studente u = new Studente("G", "A", mail, password, "22-08-1999", 000001);
        caricaDB();
        //Controllare duplicato

        map.put(String.valueOf(map.size() + 1), u);
        db.commit();
    }

    @Override
    public void getStudenti() {
        caricaDB();
        Studente[] studenti = new Studente[map.size()];
        int j = 0;
        for( String i: map.getKeys()){
            studenti[j] = map.get(i);
            j++;
        }
        System.out.println(studenti[0].getNome() + " -- " + studenti[0].matricola);
    }


    private void caricaDB(){
        this.db = getDb("./studenti.db");
        this.map = this.db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerStudente()).createOrOpen();
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
