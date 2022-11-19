package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Esame;
import com.university.client.model.Frequenta;
import com.university.client.model.Serializer.SerializerEsame;
import com.university.client.services.EsameService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EsameImpl extends RemoteServiceServlet implements EsameService {
    DB db;
    HTreeMap<Integer, Esame> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("esameDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\esame").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("esameDb", db);
            }
            return db;
        }
    }

    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("esameMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerEsame()).createOrOpen();
    }

    @Override
    public boolean creaEsame(String nomeCorso, String data, String ora, String durata, String aula) {
        try{
            createOrOpenDB();
            map.put(map.size()+1,
                    new Esame(map.size()+1, nomeCorso, data, ora, durata, aula));
            db.commit();
            return true;
        } catch(Exception e){
            System.out.println("Err: creaEsame  " + e);
        }
        return false;
    }

    @Override
    public boolean modificaEsame(int codEsame, String nomeCorso, String data, String ora, String durata, String aula) {
        try{
            createOrOpenDB();
            Esame esame = new Esame(codEsame, nomeCorso, data, ora, durata, aula);
            for(int i : map.getKeys()){
                if(map.get(i).getCodEsame() == codEsame){
                    map.replace(i, esame);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: modifica esame " + e);
        }
        return false;
    }

    @Override
    public boolean eliminaEsame(int codEsame) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getCodEsame() == codEsame){
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: Elimina esame " + e);
        }
        return false;
    }

    @Override
    public Esame getEsame(int codEsame) {
        createOrOpenDB();
        for (int id : map.getKeys()) {
            if (map.get(id).getCodEsame() == codEsame) {
                return map.get(id);
            }
        }
        return null;
    }


    @Override
    public Esame[] getEsami() {
        try{
            createOrOpenDB();
            Esame[] esami = new Esame[map.size()];
            int j = 0;
            for( int i: map.getKeys()){
                esami[j] = map.get(i);
                j++;
            }
            return esami;
        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    //restituisce gli esami di un corso
    @Override
    public Esame[] getEsamiCorso(Corso[] corsiDocente) {
        try{
            createOrOpenDB();

            ArrayList<Esame> esami = new ArrayList<>();
            Esame[] tuttiEsami = getEsami();
            for(Corso corso : Arrays.asList(corsiDocente)){
                for(Esame esame : tuttiEsami){
                    if(esame.getCodEsame() == corso.getEsame()){
                        esami.add(esame);
                    }
                }
            }
            return esami.toArray(new Esame[0]);
        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }
}
