package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.AdminService;
import com.university.client.model.Docente;
import com.university.client.model.Segreteria;
import com.university.client.model.Serializer.SerializerDocente;
import com.university.client.model.Serializer.SerializerSegreteria;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.model.Studente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.print.Doc;
import javax.servlet.ServletContext;

public class AdminImpl extends RemoteServiceServlet implements AdminService {

    /**         ~~ metodi per Studente ~~       **/

    /**
     * Crea un nuovo oggetto studente e lo salva nel DB
     */
    @Override
    public boolean creaStudente(String nome, String cognome, String password, String dataNascita) {
        try{
            DB db = getDb("C:\\Users\\gabri\\OneDrive\\Desktop\\studente.db");
            HTreeMap<String, Studente> map = db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerStudente()).createOrOpen();
            map.put(String.valueOf(map.size() + 1),
                    new Studente(nome,
                                cognome,
                                creaMailStudente(nome, cognome),
                                password,
                                dataNascita,
                                getStudenti().length));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception:" + e);
            return false;
        }
    }

    /**
     * Metodo che crea la mail per studenti nome.cognomeN@studente.university.com
     * controlla se esistono degli ononimi nel DB, nel caso aggiunge un numero N alla mail
     * come la mail unibo
     */
    public String creaMailStudente(String nome, String cognome){
        int num = 0;
        Studente[] studenti = getStudenti();
        for(int i = 0; i < studenti.length; i++){
            if(nome.equals(studenti[i].getNome()) && cognome.equals(studenti[i].getCognome())){
                num++;
            }
        }
        if(num>0){
            return nome + "." + cognome + num + "@studente.university.com";
        } else {
            return nome + "." + cognome + "@studente.university.com";
        }
    }

    /**
     * Restituisce un array di oggetti studente con tutti gli studenti presenti nel DB
     */
    @Override
    public Studente[] getStudenti() {
        try{
            DB db = getDb("C:\\Users\\gabri\\OneDrive\\Desktop\\studente.db");
            HTreeMap<String, Studente> map = db.hashMap("studentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerStudente()).createOrOpen();
            Studente[] studenti = new Studente[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                studenti[j] = map.get(i);
                j++;
            }
            return studenti;

        } catch(Exception e){
            return null;
        }
    }

    /**
     * Dato una mail restituisce tutte le informazioni di quello studente
     */
    @Override
    public String[] informazioniStudente(String mail) {
        Studente[] studenti = getStudenti();
        String [] informazioni = new String[6];
        for(int i = 0; i < studenti.length; i++){
            if(mail.equals(studenti[i].getMail())){
                informazioni[0] = studenti[i].getNome();
                informazioni[1] = studenti[i].getCognome();
                informazioni[2] = studenti[i].getMail();
                informazioni[3] = studenti[i].getPassword();
                informazioni[4] = studenti[i].getDataNascita();
                informazioni[5] = Integer.toString(studenti[i].getMatricola());
                return informazioni;
            }
        }
        return null;
    }

    /**         ~~ metodi per Docente ~~       **/
    @Override
    public boolean creaDocente(String nome, String cognome, String password) {
        try{
            DB db = getDb("C:\\docenti.db");
            HTreeMap<String, Docente> map = db.hashMap("docentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerDocente()).createOrOpen();
            map.put(String.valueOf(map.size() + 1), new Docente( nome,
                                                                    cognome,
                                                                    getMailDocente(nome, cognome),
                                                                    password,
                                                                    getDocenti().length));
            db.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getMailDocente(String nome, String cognome){
        int num = 0;
        Docente[] docenti = getDocenti();
        for(int i = 0; i < docenti.length; i++){
            if(nome.equals(docenti[i].getNome()) && cognome.equals(docenti[i].getCognome())){
                num++;
            }
        }
        if(num>0){
            return nome + "." + cognome + num + "@docente.university.com";
        } else {
            return nome + "." + cognome + "@docente.university.com";
        }
    }

    public Docente[] getDocenti(){
        try{
            DB db = getDb("C:\\docenti.db");
            HTreeMap<String, Docente> map = db.hashMap("docentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerDocente()).createOrOpen();
            Docente[] docenti = new Docente[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                docenti[j] = map.get(i);
                j++;
            }
            return docenti;

        } catch(Exception e){
            return null;
        }
    }

    @Override
    public String[] informazioniDocente(String mail) {
        return null;
    }

    /**         ~~ metodi per Segreteria ~~       **/

    @Override
    public boolean creaSegreteria(String nome, String cognome, String password) {
        try{
            DB db = getDb("C:\\segreteria.db");
            HTreeMap<String, Segreteria> map = db.hashMap("segreteriaMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerSegreteria()).createOrOpen();
            map.put(String.valueOf(map.size() + 1),
                    new Segreteria(nome,
                            cognome,
                            creaMailSegreteria(nome, cognome),
                            password));
            db.commit();
            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    public String creaMailSegreteria(String nome, String cognome){
        int num = 0;
        Segreteria[] segreteria = getSegreteria();
        for(int i = 0; i < segreteria.length; i++){
            if(nome.equals(segreteria[i].getNome()) && cognome.equals(segreteria[i].getCognome())){
                num++;
            }
        }
        if(num>0){
            return nome + "." + cognome + num + "@segreteria.university.com";
        } else {
            return nome + "." + cognome + "@segreteria.university.com";
        }
    }

    public Segreteria[] getSegreteria(){
        try{
            DB db = getDb("C:\\segreteria.db");
            HTreeMap<String, Segreteria> map = db.hashMap("segreteriaMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerSegreteria()).createOrOpen();
            Segreteria[] segreteria = new Segreteria[map.size()];
            int j = 0;
            for( String i: map.getKeys()){
                segreteria[j] = map.get(i);
                j++;
            }
            return segreteria;

        } catch(Exception e){
            return null;
        }
    }




    private DB getDb(String nomeDB) {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB");
            if(db == null){
                System.out.println("Sono qui");
                db = DBMaker.fileDB(nomeDB).closeOnJvmShutdown().make();
                context.setAttribute("DB", db);
            }
            return db;
        }
    }
}
