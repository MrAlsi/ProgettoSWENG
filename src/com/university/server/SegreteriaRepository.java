package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Segreteria;
import com.university.client.model.Serializer.SerializerSegreteria;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class SegreteriaRepository extends RemoteServiceServlet implements RepositoryString<Segreteria> {

    DB db;
    HTreeMap<Integer, Segreteria> map;
    ServletContext context;

    public SegreteriaRepository(ServletContext servletContext) {
        context = servletContext;
    }

    private DB getDb(){
        synchronized (context) {
            DB db = (DB)context.getAttribute("segreteriaDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\segreteria").closeOnJvmShutdown().checksumHeaderBypass().make();;
                context.setAttribute("segreteriaDb", db);
            }
            return db;
        }
    }
    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("segretariMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerSegreteria()).createOrOpen();
    }

    @Override
    public Segreteria GetById(String stringa) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getMail().equals(stringa)){
                    return map.get(i);
                }
            }
        } catch(Exception e){
            System.out.println("Err: getSegreteria  " + e);
        }
        return null;
    }

    @Override
    public Segreteria[] getAll() {
        try{
            createOrOpenDB();
            Segreteria[] segreteria = new Segreteria[map.size()];
            int j = 0;
            for(int i:map.getKeys()){
                segreteria[j] = map.get(i);
                j++;
            }
            return segreteria;
        } catch(Exception e){
            System.out.println("Err: getSegreteria  " + e);
            return null;
        }
    }

    @Override
    public boolean Create(Segreteria object) {
        try{
            createOrOpenDB();
            map.put((map.size() + 1),object);
            db.commit();
            return true;
        } catch(Exception e){
            System.out.println("Segreteria creata" + e);
            return false;
        }
    }

    @Override
    public boolean Remove(String stringa) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getMail().equals(stringa)){
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: Elimina segreteria " + e);
        }
        return false;
    }

    @Override
    public boolean Update(Segreteria object, String stringa) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getMail().equals(object.getMail())){
                    map.replace(i, object);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: modifica segreteria " + e);
        }
        return false;
    }
}
