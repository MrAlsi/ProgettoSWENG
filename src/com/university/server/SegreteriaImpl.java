package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Segreteria;
import com.university.client.model.Serializer.SerializerSegreteria;
import com.university.client.services.SegreteriaService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class SegreteriaImpl extends RemoteServiceServlet implements SegreteriaService {
    DB db;
    HTreeMap<Integer, Segreteria> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("segreteriaDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\segreteria").make();
                context.setAttribute("studentiDb", db);
            }
            return db;
        }
    }
    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("segretariMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerSegreteria()).createOrOpen();
    }
    @Override
    public boolean creaSegretaria(String nome, String cognome, String password) {
        try{
            createOrOpenDB();
            map.put((map.size() + 1),
                    new Segreteria(nome, cognome, creaMailSegreteria(nome, cognome), password));
            db.commit();
            return true;
        } catch(Exception e){
            System.out.println("C: AdminImpl  M: creaSegreteria: " + e);
            return false;
        }
    }
    public String creaMailSegreteria(String nome, String cognome){
        try {
            int num = 0;
            for (int i : map.getKeys()) {
                if (map.get(i).getNome() == nome && map.get(i).getCognome() == cognome) {
                    num++;
                }
            }
            if (num > 0) {
                return nome + "." + cognome + num + "@segreteria.university.com";
            } else {
                return nome + "." + cognome + "@segreteria.university.com";
            }
        } catch(Exception e){
            System.out.println("Err: creaMailSegreteria  " + e);
            return null;
        }
    }

    @Override
    public Segreteria[] getSegreteria(){
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
    public boolean modificaSegreteria(String nome, String cognome, String mail, String password) {
        try{
            createOrOpenDB();
            Segreteria segreteria = new Segreteria(nome, cognome, mail, password);
            for(int i : map.getKeys()){
                if(map.get(i).getMail().equals(mail)){
                    map.replace(i, segreteria);
                    db.commit();
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Err: modifica segreteria " + e);
        }
        return false;
    }

    @Override
    public boolean eliminaSegreteria(String mail) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getMail().equals(mail)){
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
    public Segreteria getSegreteria(String mail) {
        try{
            createOrOpenDB();
            for(int i : map.getKeys()){
                if(map.get(i).getMail().equals(mail)){
                    return map.get(i);
                }
            }
        } catch(Exception e){
            System.out.println("Err: getSegreteria  " + e);
        }
        return null;
    }
}
