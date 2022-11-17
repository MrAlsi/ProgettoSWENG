package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.model.Studente;
import com.university.client.services.DocenteService;
import com.university.client.model.Corso;
import com.university.client.model.Docente;
import com.university.client.model.Serializer.SerializerCorso;
import com.university.client.model.Serializer.SerializerDocente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class DocenteImpl extends RemoteServiceServlet implements DocenteService {
    DB db;
    HTreeMap<Integer, Docente> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("docentiDb");
            if(db == null) {
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
    public int getNumeroDocenti() {
        createOrOpenDB();
        return map.size();
    }

    @Override
    public Docente[] getDocenti() {
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
    public Docente getDocente(int codDocente) {
        createOrOpenDB();
        for (int id : map.getKeys()) {
            if (map.get(id).getCodDocente() == codDocente) {
                return map.get(id);
            }
        }
        return null;
    }

    @Override
    public Boolean eliminaDocente(int codDocente) {
        try{
            createOrOpenDB();
            for(int i:map.getKeys()){
                if(map.get(i).codDocente == codDocente){
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
    public Boolean modificaDocente(String nome, String cognome, String mail, String password, int codDocente) {
        try{
            createOrOpenDB();
            Docente docente = new Docente(nome, cognome, mail, password, codDocente);
            for(int i : map.getKeys()){
                if(map.get(i).codDocente == codDocente){
                    map.replace(i, docente);
                    db.commit();
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Err modificaDocente " + e);
        }
        return false;
    }

    @Override
    public Boolean creaDocente(String nome, String cognome, String password) {
        try{
            createOrOpenDB();
            map.put((map.size() + 1),
                    new Docente( nome, cognome,getMailDocente(nome,cognome), password, map.size()+1));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    @Override
    public Docente loginDocente(String mail, String password) {
        createOrOpenDB();
        for (int id : map.getKeys()) {
            if (map.get(id).getMail().equals(mail) && map.get(id).getPassword().equals(password)) {
                return map.get(id);
            }
        }
        return null;
    }

    public String getMailDocente(String nome, String cognome){
        int num = 0;
        for(int i : map.getKeys()){
            if(nome.equals(map.get(i).getNome()) && cognome.equals(map.get(i).getCognome())){
                num++;
            }
        }
        if(num>0){
            return nome + "." + cognome + num + "@docente.university.com";
        } else {
            return nome + "." + cognome + "@docente.university.com";
        }
    }
}
