package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Frequenta;
import com.university.client.services.CorsoService;
import com.university.client.model.Corso;
import com.university.client.model.Serializer.SerializerCorso;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;

public class CorsoImpl extends RemoteServiceServlet  implements CorsoService {

    DB db;
    HTreeMap<String, Corso> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("corsiDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\corso").make();
                context.setAttribute("corsiDb", db);
            }
            return db;
        }
    }

    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("corsiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerCorso()).createOrOpen();
    }

    @Override
    public Boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente, int esame) {
        return null;
    }

    @Override
    public Corso[] getCorsi() {
        return new Corso[0];
    }

    @Override
    public Corso getCorso(String nome) {
        return null;
    }

    @Override
    public Corso[] getMieiCorsi(int matricola) {
        return new Corso[0];
    }

    @Override
    public Corso[] getCorsiDocente(int docente) {
        return new Corso[0];
    }

    @Override
    public boolean eliminaCorso(String nome) {
        return false;
    }

    @Override
    public boolean modificaCorso(String nome, String NuovoNome, String dataInizio, String dataFine, String descrizione, int codocente, int esame) {
        return false;
    }

    @Override
    public boolean aggiungiEsame(String nome, int esame) {
        return false;
    }

    @Override
    public boolean eliminaEsame(String nome) {
        return false;
    }

    @Override
    public boolean aggiungiCodocente(String nome, int codocente) {
        return false;
    }
}
