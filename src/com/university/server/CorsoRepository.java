package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Serializer.SerializerCorso;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class CorsoRepository extends RemoteServiceServlet implements RepositoryString<Corso> {
    DB db;
    HTreeMap<Integer, Corso> map;
    ServletContext context;

    public CorsoRepository(ServletContext servletContext){
        context = servletContext;
    }
    private DB getDb(){
        synchronized (context) {
            DB db = (DB)context.getAttribute("corsiDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\corso").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("corsiDb", db);
            }
            return db;
        }
    }
    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("corsiMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerCorso()).createOrOpen();
    }

    @Override
    public Corso GetById(String stringa) {
        createOrOpenDB();
        for (int id : map.getKeys()) {
            if (map.get(id).getNome().equals(stringa)) {
                return map.get(id);
            }
        }
        return null;
    }

    @Override
    public Corso[] getAll() {
        try{
            createOrOpenDB();
            Corso[] corsi = new Corso[map.size()];
            int j = 0;
            for( int i: map.getKeys()){
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
    public boolean Create(Corso object) {
        try{
            createOrOpenDB();
            map.put(map.size() + 1, object);
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    @Override
    public boolean Remove(String stringa) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getNome().equals(stringa)){
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: Elimina corso " + e);
        }
        return false;
    }

    @Override
    public boolean Update(Corso object, String stringa) {
        createOrOpenDB();
        for(int i : map.getKeys()){
            if(map.get(i).getNome().equals(stringa)){
                map.replace(i, object);
                db.commit();
                return true;
            }
        }
        return false;
    }
}
