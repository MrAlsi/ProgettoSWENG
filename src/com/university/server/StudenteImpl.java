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
    HTreeMap<Integer,Studente> map;

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
        this.map = this.db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerStudente()).createOrOpen();
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
            for( int i: map.getKeys()){
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
        for (int id : map.getKeys()) {
            if (map.get(id).getMatricola() == matricola) {
                return map.get(id);
            }
        }
        return null;
    }

    @Override
    public Studente loginStudente(String mail, String password) {
        try{
            createOrOpenDB();
            for (int id : map.getKeys()) {
                if (map.get(id).getMail().equals(mail) && map.get(id).getPassword().equals(password)) {
                    return map.get(id);
                }
            }
        } catch(Exception e){
            System.out.println("Err login studente: " + e);
        }
        return null;
    }


    @Override
    public boolean creaStudente(String nome, String cognome, String password, String dataNascita) {
        try{
            createOrOpenDB();
            map.put((map.size() + 1),
                    new Studente( nome, cognome, creaMailStudente(nome, cognome), password, dataNascita, getNumeroStudenti()+1));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    @Override
    public boolean modificaStudente(String nome, String cognome, String mail, String password, String dataNascita, int matricola) {
        try{
            createOrOpenDB();
            Studente studente = new Studente(nome, cognome, mail, password, dataNascita, matricola);
            for(int i : map.getKeys()){
                if(map.get(i).getMatricola() == matricola){
                    map.replace(i, studente);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: modifica studente " + e);
        }
        return false;
    }

    @Override
    public boolean eliminaStudente(int matricola) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getMatricola() == matricola){
                    map.remove(i);
                    db.commit();
                    return true;

                }
            }
        } catch(Exception e){
            System.out.println("Err: Elimina studente " + e);
        }
        return false;

    }

    /**
     * Metodo che crea la mail per studenti nome.cognomeN@studente.university.com
     * controlla se esistono degli ononimi nel DB, nel caso aggiunge un numero N alla mail
     * come la mail unibo
     */
    public String creaMailStudente(String nome, String cognome){
        int num = 0;
        for(int i : map.getKeys()){
            if(nome.equals(map.get(i).getNome()) && cognome.equals(map.get(i).getCognome())){
                num++;
            }
        }
        if(num>0){
            return nome.toLowerCase() + "." + cognome.toLowerCase() + num + "@studente.university.com";
        } else {
            return nome.toLowerCase() + "." + cognome.toLowerCase() + "@studente.university.com";
        }
    }
}