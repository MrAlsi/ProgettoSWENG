package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Esame;
import com.university.client.model.Serializer.SerializerEsame;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class EsameRepository extends RemoteServiceServlet implements RepositoryInt<Esame> {

    DB db;
    HTreeMap<Integer, Esame> map;
    ServletContext context;

    public EsameRepository(ServletContext servletContext){
        context = servletContext;
    }
    private DB getDb(){
        try{
            synchronized (context) {
                DB db = (DB)context.getAttribute("esamiDb");
                if(db == null) {
                    db = DBMaker.fileDB("C:\\MapDB\\esame").closeOnJvmShutdown().checksumHeaderBypass().make();
                    context.setAttribute("esamiDb", db);
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
        this.map = this.db.hashMap("esamiMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerEsame()).createOrOpen();
    }


    @Override
    public Esame GetById(int id) {
        createOrOpenDB();
        for (int i : map.getKeys()) {
            if (map.get(i).getCodEsame() == id) {
                return map.get(i);
            }
        }
        return null;
    }

    @Override
    public Esame GetByString(String stringa) {
        return null;
    }

    @Override
    public Esame[] getAll() {
        createOrOpenDB();
        Esame[] esami = new Esame[map.size()];
        int j = 0;
        for( int i: map.getKeys()){
            esami[j] = map.get(i);
            j++;
        }
        return esami;
    }

    @Override
    public boolean Create(Esame object) {
        createOrOpenDB();
        map.put((map.size() + 1), object);
        db.commit();
        return true;
    }

    @Override
    public boolean Remove(int id) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getCodEsame()==id){
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: Esame Remove " + e);
        }
        return false;
    }

    @Override
    public boolean RemoveByString(String stringa) {
        return false;
    }

    @Override
    public boolean Update(Esame object) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getCodEsame()==object.getCodEsame()){
                    map.replace(i, object);
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
    public boolean UpdateByString(Esame object, String stringa) {
        return false;
    }
}
