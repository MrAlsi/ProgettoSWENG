package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Frequenta;
import com.university.client.model.Serializer.SerializerCorso;
import com.university.client.model.Serializer.SerializerFrequenta;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class FrequentaRepository extends RemoteServiceServlet implements RepositoryIntString<Frequenta> {
    DB db;
    HTreeMap<Integer, Frequenta> map;
    ServletContext context;

    public FrequentaRepository(ServletContext servletContext){
        context = servletContext;
    }
    private DB getDb(){
        try{
            synchronized (context) {
                DB db = (DB)context.getAttribute("frequentaDb");
                if(db == null) {
                    db = DBMaker.fileDB("C:\\MapDB\\frequenta").closeOnJvmShutdown().checksumHeaderBypass().make();
                    context.setAttribute("frequentaDb", db);
                }
                return db;
            }
        } catch(Exception e){
            System.out.println("-----Errore: " + e);
            return null;
        }
    }

    protected void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("frequentaMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerFrequenta()).createOrOpen();
    }

    @Override
    public Frequenta[] getAll() {
        createOrOpenDB();
        Frequenta[] frequenta = new Frequenta[map.size()];
        int j = 0;
        for( int i: map.getKeys()){
            frequenta[j] = map.get(i);
            j++;
        }
        return frequenta;
    }

    @Override
    public Frequenta[] getArrayByInt(int id) {
        try {
            createOrOpenDB();
            Frequenta[] frequenta = getAll();
            ArrayList<Frequenta> mieiCorsi = new ArrayList<>();
            for(Frequenta corso : frequenta) {
                if (corso.matricola == id) {
                    mieiCorsi.add(corso);
                }
            }
            return mieiCorsi.toArray(new Frequenta[0]);
        }catch (Exception e){
            System.out.println("Errore: " + e);
            return null;
        }
    }

    @Override
    public Frequenta[] getArrayByString(String id) {
        try {
            createOrOpenDB();
            Frequenta[] frequenta = getAll();
            ArrayList<Frequenta> studenti = new ArrayList<>();
            for(Frequenta studente: frequenta) {
                if (studente.nomeCorso.equals(id)) {
                    studenti.add(studente);
                }
            }
            return studenti.toArray(new Frequenta[0]);
        }catch (Exception e){
            System.out.println("Errore: " + e);
            return null;
        }
    }

    @Override
    public boolean Create(Frequenta object) {
        createOrOpenDB();
        map.put((map.size() + 1), object);
        db.commit();
        return true;
    }

    @Override
    public boolean Remove(int id, String nome) {
        try{
            createOrOpenDB();
            for(int i:map.getKeys()){
                if(map.get(i).getMatricola() == id && map.get(i).getNomeCorso().equals(nome)){
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }

        }catch(Exception e){
            System.out.println("Err: elimina iscrizione " + e);
        }
        return false;
    }
}
