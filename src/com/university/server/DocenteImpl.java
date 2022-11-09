package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.model.Studente;
import com.university.client.services.DocenteService;
import com.university.client.model.Corso;
import com.university.client.model.Docente;
import com.university.client.model.Serializer.SerializerCorso;
import com.university.client.model.Serializer.SerializerDocente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class DocenteImpl extends RemoteServiceServlet implements DocenteService {
    DB db;
    HTreeMap<String, Docente> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("docentiDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\docente").make();
                context.setAttribute("docentiDb", db);
            }
            return db;
        }
    }

    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("docentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerDocente()).createOrOpen();
    }

    @Override
    public Docente[] getDocenti() {
        try{
            createOrOpenDB();
            Docente[] docenti = new Docente[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                docenti[j] = map.get(i);
                j++;
            }
            return docenti;
        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    @Override
    public Docente getDocente(int codDocente) {
        createOrOpenDB();
        for (String id : map.getKeys()) {
            if (map.get(id).getCodDocente() == codDocente) {
                return map.get(id);
            }
        }
        return null;
    }

    @Override
    public Boolean eliminaDocente(int codDocente) {
        return null;
    }

    @Override
    public Boolean modificaDocente(String nome, String cognome, String mail, String password, int codDocente) {
        return null;
    }

    @Override
    public Boolean creaDocente(String nome, String cognome, String password, int codDocente) {
        try{
            createOrOpenDB();
            map.put(String.valueOf(map.size() + 1),
                    new Docente( nome, cognome,getMailDocente(nome,cognome), password,codDocente));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }
    public String getMailDocente(String nome, String cognome){
        int num = 0;
        Docente[] docenti = getDocenti();
        for(int i = 0; i < docenti.length; i++){
            if(nome.equals(docenti[i].getNome()) && cognome.equals(docenti[i].getCognome())){
                num++;
            }
        }
        if(num>0){
            return nome + "." + cognome + num + "@docente.university.com";
        } else {
            return nome + "." + cognome + "@docente.university.com";
        }
    }
}

/*
    @Override
    public Docente getInfoPersonali(int codDocente) {
        try{
            Docente[] docenti = super.getDocenti();
            for(Docente docente: docenti){
                if(docente.codDocente==codDocente){
                    return docente;
                }
            }
            return null;
        }catch(Exception e){
            System.out.println("errore Docente - getinfoPersonali: "+e);
            return null;
        }
    }



    public boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame) {
        try {
            //super.creaCorso(nome, dataInizio, dataFine, descrizione, coDocente, docente, esame);
            return true;
        } catch (Exception e){
            return false;
        }
    }*/
