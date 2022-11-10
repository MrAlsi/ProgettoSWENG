package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.*;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.services.StudenteService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;

public class StudenteImpl extends RemoteServiceServlet implements StudenteService {
    DB db;
    HTreeMap<String,Studente> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("studentiDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\studente").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("studentiDb", db);
            }
            return db;
        }
    }
    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerStudente()).createOrOpen();
    }

    @Override
    public int getNumeroStudenti() {
        createOrOpenDB();
        return map.size();
    }

    @Override
    public Studente[] getStudenti() {
        try{
            createOrOpenDB();
            Studente[] studenti = new Studente[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                studenti[j] = map.get(i);
                j++;
            }
            return studenti;

        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    @Override
    public Studente getStudenteByMatricola(int matricola) {
        createOrOpenDB();
        for (String id : map.getKeys()) {
            if (map.get(id).getMatricola() == matricola) {
                return map.get(id);
            }
        }
        return null;
    }


    @Override
    public boolean creaStudente(String nome, String cognome, String password, String dataNascita) {
        try{
            createOrOpenDB();
            map.put(String.valueOf(map.size() + 1),
                    new Studente( nome, cognome, creaMailStudente(nome, cognome), password, dataNascita, getNumeroStudenti()+1));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    @Override
    public boolean eliminaStudente(int matricola) {
        return false;
    }

    /**
     * Metodo che crea la mail per studenti nome.cognomeN@studente.university.com
     * controlla se esistono degli ononimi nel DB, nel caso aggiunge un numero N alla mail
     * come la mail unibo
     */
    public String creaMailStudente(String nome, String cognome){
        int num = 0;
        Studente[] studenti = getStudenti();
        for(int i = 0; i < studenti.length; i++){
            if(nome.equals(studenti[i].getNome()) && cognome.equals(studenti[i].getCognome())){
                num++;
            }
        }
        if(num>0){
            return nome + "." + cognome + num + "@studente.university.com";
        } else {
            return nome + "." + cognome + "@studente.university.com";
        }
    }
}