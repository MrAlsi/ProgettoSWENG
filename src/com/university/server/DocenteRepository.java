package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Docente;
import com.university.client.model.Serializer.SerializerDocente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class DocenteRepository extends RemoteServiceServlet implements RepositoryInt<Docente> {
    DB db;
    HTreeMap<Integer, Docente> map;
    ServletContext context;
    public DocenteRepository(ServletContext servletContext){
        context = servletContext;
    }

    private DB getDb(){
        synchronized (context) {
            DB db = (DB) context.getAttribute("docentiDb");
            if (db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\docente").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("docentiDb", db);
            }
            return db;
        }
    }

    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("docentiMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerDocente()).createOrOpen();
    }

    @Override
    public Docente GetById(int id) {
        createOrOpenDB();
        for (int i : map.getKeys()) {
            if (map.get(i).getCodDocente() == id) {
                return map.get(i);
            }
        }
        return null;
    }

    @Override
    public Docente[] getAll() {
        try{
            createOrOpenDB();
            Docente[] docenti = new Docente[map.size()];
            int j = 0;
            for( int i: map.getKeys()){
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
    public boolean Create(Docente object) {
        try{
            createOrOpenDB();
            map.put((map.size() + 1), object);
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    @Override
    public boolean Remove(int id) {
        try{
            createOrOpenDB();
            for(int i:map.getKeys()){
                if(map.get(i).codDocente == id){
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }

        }catch(Exception e){
            System.out.println("Err: elimina docente " + e);
        }
        return false;
    }

    @Override
    public boolean Update(Docente object) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).codDocente == object.getCodDocente()){
                    map.replace(i, object);
                    db.commit();
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Err modificaDocente " + e);
        }
        return false;
    }
}
