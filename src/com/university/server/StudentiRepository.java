package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.model.Studente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class StudentiRepository extends RemoteServiceServlet implements RepositoryInt<Studente> {

    DB db;
    HTreeMap<Integer,Studente> map;
    ServletContext context;
    public StudentiRepository(ServletContext servletContext){
        context = servletContext;
    }
    private DB getDb(){
        try{
            synchronized (context) {
                DB db = (DB)context.getAttribute("studentiDb");
                if(db == null) {
                    db = DBMaker.fileDB("C:\\MapDB\\studente").closeOnJvmShutdown().checksumHeaderBypass().make();
                    context.setAttribute("studentiDb", db);
                }
                return db;
            }
        } catch(Exception e){
            return null;
        }
    }

    protected void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerStudente()).createOrOpen();
    }

    @Override
    public Studente GetById(int id) {
        createOrOpenDB();
        for (int matricola : map.getKeys()) {
            if (map.get(matricola).getMatricola() == id) {
                return map.get(matricola);
            }
        }
        return null;
    }

    @Override
    public Studente[] getAll() {
        createOrOpenDB();
        Studente[] studenti = new Studente[map.size()];
        int j = 0;
        for( int i: map.getKeys()){
            studenti[j] = map.get(i);
            j++;
        }
        return studenti;
    }

    @Override
    public boolean Create(Studente object) {
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
                if(map.get(i).getMatricola()==id){
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: Studenti Remove " + e);
        }
        return false;
    }

    @Override
    public boolean Update(Studente object) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getMatricola() == object.getMatricola()){
                    map.replace(i, object);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: Studenti Update " + e);
        }
        return false;
    }
}
