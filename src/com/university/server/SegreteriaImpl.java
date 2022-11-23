package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Docente;
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
    public boolean creaSegretaria(String nome, String cognome, String password) {
        try{
            createOrOpenDB();
            map.put((map.size() + 1),
                    new Segreteria(nome, cognome, creaMailSegreteria(nome, cognome), password));
            db.commit();
            return true;
        } catch(Exception e){
            System.out.println("C: SegreteriaImpl  M: creaSegreteria: " + e);
            return false;
        }
    }
    public String creaMailSegreteria(String nome, String cognome){
        int num = 0;
        //Segreteria[] segreteria = getSegreteria();
        for(int i : map.getKeys()) {
            if (nome.equals(map.get(i).getNome()) && cognome.equals(map.get(i).getCognome())) {
                num++;
            }
        }
        if(num>0){
            return nome.toLowerCase() + "." + cognome.toLowerCase() + num + "@segreteria.university.com";
        } else {
            return nome.toLowerCase() + "." + cognome.toLowerCase() + "@segreteria.university.com";
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

    @Override
    public Segreteria loginSegreteria(String mail, String password) {
        createOrOpenDB();
        for (int id : map.getKeys()) {
            if (map.get(id).getMail().equals(mail) && map.get(id).getPassword().equals(password)) {
                return map.get(id);
            }
        }
        return null;
    }
}
