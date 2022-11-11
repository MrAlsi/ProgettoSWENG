package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Docente;
import com.university.client.model.Frequenta;
import com.university.client.model.Serializer.SerializerDocente;
import com.university.client.model.Serializer.SerializerSostiene;
import com.university.client.model.Sostiene;
import com.university.client.services.SostieneService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class SostieneImpl extends RemoteServiceServlet implements SostieneService {

    DB db;
    HTreeMap<Integer, Sostiene> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("sostieneDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\sostiene").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("sostieneDb", db);
            }
            return db;
        }
    }

    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("sostieneMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerSostiene()).createOrOpen();
    }

    //ottengo un array con tutte le istanze di sostiene
    @Override
    public Sostiene[] getSostiene() {
        try{
            createOrOpenDB();
            Sostiene[] sostenuti = new Sostiene[map.size()];
            int j = 0;
            for( int i: map.getKeys()){
                sostenuti[j] = map.get(i);
                j++;
            }
            return sostenuti;
        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    //ottengo un array con gli esami sostenuti di uno studente
    @Override
    public Sostiene[] getSostieneStudente(int matricola) {
        try{
            createOrOpenDB();
            ArrayList<Sostiene> sostenuti=new ArrayList<>();
            Sostiene[] sostiene= getSostiene();
            for(Sostiene esame: sostiene){
                if(esame.getMatricola()==matricola){
                    sostenuti.add(esame);
                }
            }
            return sostenuti.toArray(new Sostiene[0]);
        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    //ottengo i voti di tutti gli studenti che hanno dato l'esame
    @Override
    public Sostiene[] getStudenti(int codEsame) {
        try{
            createOrOpenDB();
            ArrayList<Sostiene> sostenuti=new ArrayList<>();
            Sostiene[] sostiene= getSostiene();
            for(Sostiene esame: sostiene){
                if(esame.getCodEsame() == codEsame){
                    sostenuti.add(esame);
                }
            }
            return sostenuti.toArray(new Sostiene[0]);
        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    //creo una nuova istanza di sostiene
    @Override
    public boolean creaSostiene(int matricola, int codEsame, int voto) {
        try{
            createOrOpenDB();
            map.put((map.size() + 1),
                    new Sostiene( matricola, codEsame,voto));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }
}
