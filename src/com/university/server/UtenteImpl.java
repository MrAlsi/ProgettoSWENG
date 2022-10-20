package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.UtenteService;
import com.university.client.model.Docente;
import com.university.client.model.Segreteria;
import com.university.client.model.Serializer.SerializerDocente;
import com.university.client.model.Serializer.SerializerSegreteria;
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
import java.nio.file.Path;
import java.nio.file.Paths;

public class UtenteImpl extends RemoteServiceServlet implements UtenteService {

    @Override
    public Utente login(String mail, String password) {
        String[] dominio = mail.split("@");
        System.out.println(dominio[1]);
        //Switch per vedere il dominio e cercare in un determinato database
        return switch (dominio[1]) {
            case "studente.university.com" -> cercaStudenti(mail, password);
            case "docente.university.com" -> cercaDocente(mail, password);
            default -> cercaSegreteria(mail, password);
        };
    }

    public Studente cercaStudenti(String mail, String password){
        Studente[] studenti = getStudenti();
        for (Studente studente : studenti) {
            if (mail.equals(studente.getMail()) && password.equals(studente.getPassword()))
                return studente;
        }
        return null;
    }

    @Override
    public Studente[] getStudenti(){
        try{
            DB db = getDb("C:\\Users\\gabri\\OneDrive\\Desktop\\studenti.db");
            HTreeMap<String, Studente> map = db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerStudente()).createOrOpen();
            Studente[] studenti = new Studente[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                studenti[j] = map.get(i);
                j++;
            }
            return studenti;

        } catch(Exception e){
            return null;
        }
    }

    public Docente cercaDocente(String mail, String password){
        Docente[] docenti = getDocenti();
        for (Docente docente : docenti) {
            if (mail.equals(docente.getMail()) && password.equals(docente.getPassword()))
                return docente;
        }
        return null;
    }

    public Docente[] getDocenti(){
        try{
            DB db = getDb("C:\\docenti.db");
            HTreeMap<String, Docente> map = db.hashMap("docentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerDocente()).createOrOpen();
            Docente[] docenti = new Docente[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                docenti[j] = map.get(i);
                j++;
            }
            return docenti;

        } catch(Exception e){
            return null;
        }
    }

    public Segreteria cercaSegreteria(String mail, String password){
        Segreteria[] segreteria = getSegreteria();
        for (Segreteria value : segreteria) {
            if (mail.equals(value.getMail()) && password.equals(value.getPassword()))
                return value;
        }
        return null;
    }

    public Segreteria[] getSegreteria(){
        try{
            DB db = getDb("C:\\segreteria.db");
            HTreeMap<String, Segreteria> map = db.hashMap("segreteriaMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerSegreteria()).createOrOpen();
            Segreteria[] segreteria = new Segreteria[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                segreteria[j] = map.get(i);
                j++;
            }
            return segreteria;

        } catch(Exception e){
            return null;
        }
    }

    private DB getDb(String nomeDB) {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB");
            if(db == null){

                db = DBMaker.fileDB(nomeDB).closeOnJvmShutdown().make();
                Path path = Paths.get("studenti");
                System.out.println(path);
                context.setAttribute("DB", db);
            }
            return db;
        }
    }
}