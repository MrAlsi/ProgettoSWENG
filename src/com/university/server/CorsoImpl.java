package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.CorsoService;
import com.university.client.model.Corso;
import com.university.client.model.Serializer.SerializerCorso;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class CorsoImpl extends RemoteServiceServlet implements CorsoService {
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
    @Override
    public Corso cercaCorsoByNomeCorso(String nome){
        try{
            DB db = getDb("C:\\MapDB\\corsi");
            HTreeMap<String, Corso> map = db.hashMap("corsoMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerCorso()).createOrOpen();
            for(Corso c: map.getValues()){
                if(c.nome==nome){
                    return c;
                }
            }
            System.out.println("il corso cercato non esiste");
            return null;
        }
        catch (Exception e){
            System.out.println("Exception: " + e);
            return null;
        }
    }

    @Override
    public Corso[] cercaCorsoByDocente(int docente){
        try{
            DB db = getDb("C:\\MapDB\\corsi");
            HTreeMap<String, Corso> map = db.hashMap("corsoMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerCorso()).createOrOpen();
            Corso[] corsi= new Corso[map.size()];
            int j = 0;
            for(Corso c: map.getValues()){
                if(c.docente==docente){
                    corsi[j] = c;
                    j++;
                }
            }
            return corsi;
        }
        catch (Exception e){
            System.out.println("Exception: " + e);
            return null;
        }
    }

    @Override
    //i controlli su quello che viene inserito sono da un'altra parte: [mettere nome metodo]
    public boolean modificaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int esame, Corso corso){
        DB db = getDb("C:\\MapDB\\corsi");
        corso.nome=nome;
        corso.dataInizio=dataInizio;
        corso.dataFine=dataFine;
        corso.descrizione=descrizione;
        corso.coDocente =coDocente;
        corso.esame=esame;
        db.commit();
        return true;
    }

    private DB getDb(String nomeDB) {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB");
            if(db == null){
                db = DBMaker.fileDB(nomeDB).closeOnJvmShutdown().make();
                context.setAttribute("DB", db);
            }
            return db;
        }
    }
}
