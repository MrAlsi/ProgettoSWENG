package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.*;
import com.university.client.model.Serializer.*;
import com.university.client.services.*;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FrequentaImpl extends RemoteServiceServlet implements FrequentaService {

    DB db;
    HTreeMap<Integer, Frequenta> map;

    private DB getDb(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("frequentaDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\frequenta").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("frequentaDb", db);
            }
            return db;
        }
    }

    private void createOrOpenDB(){
        this.db = getDb();
        this.map = this.db.hashMap("frequentaMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerFrequenta()).createOrOpen();
    }


    //chiamata al db corsi
    private DB getCorsiDB(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("corsiDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\corso").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("corsiDb", db);
            }
            return db;
        }
    }

    //metodo che mi permette di avere qui in frequenza anche il db dei corsi
    private Corso[] traduciCorso(){
        try{
            DB dbCorsi=getCorsiDB();
            HTreeMap<Integer, Corso> mapCorsi = dbCorsi.hashMap("corsiMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerCorso()).createOrOpen();
            Corso[] corsi = new Corso [mapCorsi.size()];
            int j=0;
            for(int i: mapCorsi.getKeys()){
                corsi[j]=mapCorsi.get(i);
                j++;
            }
            return corsi;
        }catch (Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    //chiamata al db corsi
    private DB getSostieneDB(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("sostieneDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\sostiene").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("sostieneDb", db);
            }
            return db;
        }
    }

    //metodo che mi permette di avere qui in frequenza anche il db dei corsi
    private Sostiene[] traduciSostiene(){
        try{
            DB dbSostiene=getSostieneDB();
            HTreeMap<Integer, Sostiene> mapSostiene = dbSostiene.hashMap("sostieneMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerSostiene()).createOrOpen();
            Sostiene[] sostiene = new Sostiene [mapSostiene.size()];
            int j=0;
            for(int i: mapSostiene.getKeys()){
                sostiene[j]=mapSostiene.get(i);
                j++;
            }
            return sostiene;
        }catch (Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }


    //metodo per prendere tutte le istanze di frequenta
    @Override
    public Frequenta[] getFrequenta() {
        try{
            createOrOpenDB();
            Frequenta[] frequenta = new Frequenta[map.size()];
            int j = 0;
            for( int i: map.getKeys()){
                frequenta[j] = map.get(i);
                j++;
            }
            return frequenta;

        } catch(Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    //metodo che mostra i corsi a cui lo studente non è ancora iscritto
    @Override
    public Corso[] getCorsiDisponibili(int matricola) {
        try {
            createOrOpenDB();

            //prendo tutti i corsi
            Boolean check;

            Sostiene[] tuttiSostiene = traduciSostiene();
            Corso[] tuttiCorsi = traduciCorso();

            ArrayList<Corso> corsiDisponibili = new ArrayList<>();
            ArrayList<Corso> corsiDisponibiliFinali = new ArrayList<>();

            HashMap<String, Corso> corsi = new HashMap<>();

            for (Corso corso : tuttiCorsi) {
                corsi.put(corso.nome, corso);
            }

            ArrayList<Frequenta> mieiCorsi = getMieiCorsi(matricola);

            //filtro solo i corsi non superati
            for (Corso corso : corsi.values()) {
                check = false;
                for(Sostiene sostiene : tuttiSostiene) {
                    if (sostiene.getMatricola() == matricola
                            && sostiene.getNomeCorso().equals(corso.getNome())
                            && sostiene.getAccettato()
                            && sostiene.getVoto() >= 18) {
                        check = true;
                    }
                }
                if (!check) {
                    corsiDisponibili.add(corso);
                }
            }

            //filtro i corsi ai quali non sono iscritto
            for (Corso corso : corsiDisponibili) {
                check = false;
                for (Frequenta frequenta : mieiCorsi) {
                    if (corso.getNome().equals(frequenta.getNomeCorso())) {
                        check = true;
                    }
                }
                if (!check) {
                    corsiDisponibiliFinali.add(corso);
                }
            }

            return corsiDisponibiliFinali.toArray(new Corso[0]);


        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
    }

    //metodo per prendere i corsi di uno studente in formato Corso[]
    //prendo solo i corsi che lo studente frequenta che non hanno ricevuto un voto >= 18.
    //se lo studente prende un'insufficienza rimane iscritto al corso, se lo passa si disicrive
    public Corso[] getCorsiStudente(int matricola) {
        try {
            createOrOpenDB();

            Boolean check = false;
            Corso[] tuttiCorsi = traduciCorso();
            Sostiene[] tuttiSostiene = traduciSostiene();

            ArrayList<Corso> corsiDisponibili = new ArrayList<>();
            HashMap<String, Corso> corsi = new HashMap<>();

            ArrayList<Frequenta> mieiCorsi = getMieiCorsi(matricola);

            //smisto i doppioni attraverso un hashmap
            for (Corso corso : tuttiCorsi) {
                for (Frequenta frequenta : mieiCorsi) {
                    for (Sostiene sostiene : tuttiSostiene) {
                        check = false;
                        if (corso.getNome().equals(frequenta.getNomeCorso())
                                && corso.getNome().equals(sostiene.getNomeCorso())
                                && sostiene.getMatricola() == frequenta.getMatricola()
                                && sostiene.getAccettato()
                                && sostiene.getVoto() >= 18) {
                            check = true;
                        }
                    }
                    if (corso.getNome().equals(frequenta.getNomeCorso())
                            && !check) {
                        corsi.put(corso.nome, corso);
                    }
                }
            }

            for(Corso corso : corsi.values()){
                corsiDisponibili.add(corso);
            }

            return corsiDisponibili.toArray(new Corso[0]);
        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
    }

    //metodo per prendere i miei corsi
    @Override
    public ArrayList<Frequenta> getMieiCorsi(int matricola) {
        try {
            createOrOpenDB();
            Frequenta[] frequenta = getFrequenta();
            ArrayList<Frequenta> mieiCorsi = new ArrayList<>();
            for(Frequenta corso : frequenta) {
                if (corso.matricola == matricola) {
                    mieiCorsi.add(corso);
                }
            }
            return mieiCorsi;

        }catch (Exception e){
            System.out.println("Errore: " + e);
            return null;
        }
    }

    //metodo per vedere gli studenti iscritti a un corso
    @Override
    public ArrayList<Frequenta> getStudentiIscritti(String nomeCorso) {
        try {
            createOrOpenDB();
            Frequenta[] frequenta = getFrequenta();
            ArrayList<Frequenta> studenti = new ArrayList<>();
            for(Frequenta studente: frequenta) {
                if (studente.nomeCorso.equals(nomeCorso)) {
                    studenti.add(studente);
                }
            }
            return studenti;
        }catch (Exception e){
            System.out.println("Errore: " + e);
            return null;
        }

    }

    //metodo per creare una nuova istanza di frequenta, al momento non sono ancora implementati i controlli
    @Override
    public boolean iscrivi(int matricola, String nomeCorso) {
        try{
            createOrOpenDB();
            map.put((map.size() + 1),
                    new Frequenta( matricola, nomeCorso));
            db.commit();
            return true;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return false;
        }
    }

    @Override
    public boolean cancellaIscrizione(int matricola, String nomeCorso) {
        try{
            createOrOpenDB();
            for(int i:map.getKeys()){
                if(map.get(i).getMatricola() == matricola && map.get(i).getNomeCorso().equals(nomeCorso)){
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
