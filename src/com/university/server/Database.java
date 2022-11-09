package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.*;
import com.university.client.model.Serializer.*;
import com.university.client.services.DatabaseService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class Database extends RemoteServiceServlet implements DatabaseService {
    HTreeMap<String, Studente> studentiDB;
    @Override
    public void creaDB() {

    }

    @Override
    public void studentiDB() {
        DB db = getDb("C:\\MapDB\\corsi");
        this.studentiDB = db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerStudente()).createOrOpen();


    }
    public DB getDb(String nomeDB) {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB");
            if(db != null) {
                db.close();
            }

            db = DBMaker.fileDB(nomeDB).closeOnJvmShutdown().checksumHeaderBypass().make();
            context.setAttribute("DB", db);

            return db;
        }
    }

    @Override
    public Admin[] getAdmin() {
        try{
            DB db = getDb("C:\\MapDB\\admin");
            HTreeMap<String, Admin> map = db.hashMap("adminMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerAdmin()).createOrOpen();
            Admin[] admin = new Admin[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                admin[j] = map.get(i);
                j++;
            }
            return admin;

        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }


    //metodi che lavorano su Corso
    @Override
    public Corso[] getCorsi() {
        try{
            DB db = getDb("C:\\MapDB\\corsi");
            HTreeMap<String, Corso> map = db.hashMap("corsiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerCorso()).createOrOpen();
            Corso[] corsi = new Corso[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                corsi[j] = map.get(i);
                j++;
            }
            return corsi;

        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    @Override
    public boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame) {
        try{
            DB db = getDb("C:\\MapDB\\corsi");
            HTreeMap<String, Corso> map = db.hashMap("corsoMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerCorso()).createOrOpen();
            map.put(String.valueOf(map.size() + 1),
                    new Corso( nome, dataInizio, dataFine, descrizione, coDocente, docente, esame));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    //metodi per lavorare sui docenti
    @Override
    public Docente[] getDocenti() {
        try{
            DB db = getDb("C:\\MapDB\\docenti");
            HTreeMap<String, Docente> map = db.hashMap("docenteMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerDocente()).createOrOpen();
            Docente[] docenti = new Docente[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                docenti[j] = map.get(i);
                j++;
            }
            db.close();
            return docenti;

        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    @Override
    public boolean creaDocenti(String nome, String cognome, String mail, String password, int codDocente) {
        try{
            DB db = getDb("C:\\MapDB\\docenti");
            HTreeMap<String, Docente> map = db.hashMap("docenteMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerDocente()).createOrOpen();
            map.put(String.valueOf(map.size() + 1),
                    new Docente( nome, cognome, mail, password, codDocente));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }


    //metodi per lavorare sugli esami
    @Override
    public Esame[] getEsami() {
        try{
            DB db = getDb("C:\\MapDB\\esami.db");
            HTreeMap<String, Esame> map = db.hashMap("esamiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerEsame()).createOrOpen();
            Esame[] esami = new Esame[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                esami[j] = map.get(i);
                j++;
            }
            return esami;

        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    @Override
    public boolean creaEsami(int codEsame, String nomeCorso, String data, String ora, String durata, String aula) {
        try{
            DB db = getDb("C:\\MapDB\\esami");
            HTreeMap<String, Esame> map = db.hashMap("esameMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerEsame()).createOrOpen();
            map.put(String.valueOf(map.size() + 1),
                    new Esame( codEsame, nomeCorso, data, ora, durata, aula));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    //metodi per lavorare su Frequenta
    @Override
    public Frequenta[] getFrequenta() {
        try{
            DB db = getDb("C:\\MapDB\\frequenta");
            HTreeMap<String, Frequenta> map = db.hashMap("frequentaMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerFrequenta()).createOrOpen();
            Frequenta[] frequenta = new Frequenta[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                frequenta[j] = map.get(i);
                j++;
            }
            return frequenta;

        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    @Override
    public boolean creaFrequenta(int matricola, String nomeCorso){
        try{
            DB db = getDb("C:\\MapDB\\frequenta");
            HTreeMap<String, Frequenta> map = db.hashMap("frequentaMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerFrequenta()).createOrOpen();
            map.put(String.valueOf(map.size() + 1),
                    new Frequenta( matricola, nomeCorso));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    //metodi per lavorare sulla segreteria
    @Override
    public Segreteria[] getSegretari() {
        try{
            DB db = getDb("C:\\MapDB\\segretari");
            HTreeMap<String, Segreteria> map = db.hashMap("segretariMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerSegreteria()).createOrOpen();
            Segreteria[] segretari= new Segreteria[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                segretari[j] = map.get(i);
                j++;
            }
            return segretari;

        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    @Override
    public boolean creaSegratario(String nome, String cognome, String mail, String password) {
        try{
            DB db = getDb("C:\\MapDB\\segretari");
            HTreeMap<String, Segreteria> map = db.hashMap("corsoMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerSegreteria()).createOrOpen();
            map.put(String.valueOf(map.size() + 1),
                    new Segreteria( nome, cognome, mail, password));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    //metodi per lavorare su sostiene
    @Override
    public Sostiene[] getSostiene() {
        try{
            DB db = getDb("C:\\MapDB\\sostiene");
            HTreeMap<String, Sostiene> map = db.hashMap("sostieneMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerSostiene()).createOrOpen();
            Sostiene[] sostiene = new Sostiene[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                sostiene[j] = map.get(i);
                j++;
            }
            return sostiene;

        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    @Override
    public boolean creaSostiene(int matricola, int codEsame, int voto) {
        try{
            DB db = getDb("C:\\MapDB\\sostiene");
            HTreeMap<String, Sostiene> map = db.hashMap("sostieneMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerSostiene()).createOrOpen();
            map.put(String.valueOf(map.size() + 1),
                    new Sostiene( matricola, codEsame, voto));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    //metodi per lavorare su studenti
    @Override
    public Studente[] getStudenti() {
        try{
            DB db = getDb("C:\\MapDB\\studenti");
            HTreeMap<String, Studente> map = db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerStudente()).createOrOpen();
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
    public boolean creaStudente(String nome, String cognome, String mail, String password, String dataNascita, int matricola) {
        try{
            DB db = getDb("C:\\MapDB\\studente");
            HTreeMap<String, Studente> map = db.hashMap("studenteMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerStudente()).createOrOpen();
            map.put(String.valueOf(map.size() + 1),
                    new Studente( nome, cognome, mail, password, dataNascita, matricola));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }
}
