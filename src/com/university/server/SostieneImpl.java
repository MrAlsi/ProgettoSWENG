package com.university.server;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.*;
import com.university.client.model.Serializer.SerializerDocente;
import com.university.client.model.Serializer.SerializerEsame;
import com.university.client.model.Serializer.SerializerSostiene;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.services.SostieneService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;

public class SostieneImpl {} /*extends RemoteServiceServlet implements SostieneService {

    DB db;
    HTreeMap<Integer, Sostiene> map;
    HTreeMap<Integer, Esame> mapEsami;

    private DB getDb() {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("sostieneDb");
            if (db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\sostiene").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("sostieneDb", db);
            }
            return db;
        }
    }

    //chiamata al db esami
    private DB getEsamiDB(){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("esameDb");
            if(db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\esame").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("esameDb", db);
            }
            return db;
        }
    }

    //metodo che mi permette di avere qui in frequenza anche il db degli esami
    private Esame[] traduciEsame(){
        try{
            DB dbEsami = getEsamiDB();
            HTreeMap<Integer, Esame> mapEsami = dbEsami.hashMap("esameMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerEsame()).createOrOpen();
            Esame[] esami = new Esame [mapEsami.size()];

            int j=0;
            for(int i: mapEsami.getKeys()){
                esami[j]=mapEsami.get(i);
                j++;
            }
            return esami;
        }catch (Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }

    private void createOrOpenDB() {
        this.db = getDb();
        this.map = this.db.hashMap("sostieneMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerSostiene()).createOrOpen();
    }

    //ottengo un array con tutte le istanze di sostiene
    @Override
    public Sostiene[] getSostiene() {
        try {
            createOrOpenDB();
            Sostiene[] sostenuti = new Sostiene[map.size()];
            int j = 0;
            for (int i : map.getKeys()) {
                sostenuti[j] = map.get(i);
                j++;
            }
            return sostenuti;
        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
    }

    //ottengo un array con gli esami sostenuti di uno studente
    @Override
    public Sostiene[] getSostieneStudenteSenzaVoto(int matricola) {
        try {
            createOrOpenDB();
            Sostiene[] sostenuti = new Sostiene[map.size()];
            int j = 0;
            for (int i : map.getKeys()) {
                if (map.get(i).getMatricola() == matricola && map.get(i).voto == -1) {
                    sostenuti[j] = map.get(i);
                    j++;
                }
            }
            return sostenuti;
        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
    }

    //ottengo i voti di tutti gli studenti che hanno dato l'esame e cui non è stato assegnato un voto
   /* @Override
    public Sostiene[] getSostieneStudenteConVoto(int matricola) {
        try {
            createOrOpenDB();
            ArrayList<Sostiene> sostenuti = new ArrayList<>();
            Sostiene[] sostiene = getSostiene();
            for (Sostiene esame : sostiene) {
                if (esame.getMatricola() == matricola && esame.voto != -1) {
                    sostenuti.add(esame);
                }
            }
            return sostenuti.toArray(new Sostiene[0]);
        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
    }*/

    //ottengo i voti dello studente
 /*   @Override
    public Sostiene[] getEsamiLibretto(int matricola) {
        try {
            createOrOpenDB();
            ArrayList<Sostiene> sostenuti = new ArrayList<>();
            Sostiene[] sostiene = getSostiene();
            for (Sostiene esame : sostiene) {
                if (esame.getMatricola() == matricola && esame.accettato) {
                    sostenuti.add(esame);
                }
            }
            return sostenuti.toArray(new Sostiene[0]);
        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
    }

    @Override
    public Sostiene[] getStudenti(int codEsame) {
        try {
            createOrOpenDB();
            ArrayList<Sostiene> sostenuti = new ArrayList<>();
            //Sostiene[] sostiene= getSostiene();
            for (int i : map.getKeys()) {
                if (map.get(i).getCodEsame() == codEsame) {
                    sostenuti.add(map.get(i));
                }
            }
            return sostenuti.toArray(new Sostiene[0]);
        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
    }

    @Override
    public boolean inserisciVoto(int esame, int matricola, int voto) {
        try {
            createOrOpenDB();
            for (int i : map.getKeys()) {
                if (map.get(i).getCodEsame() == esame && map.get(i).getMatricola() == matricola) {
                    map.replace(i, new Sostiene(matricola, esame, map.get(i).nomeCorso,map.get(i).data, map.get(i).ora,  voto, false));
                    db.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Err: inserisci voto: " + e);
        }
        return false;
    }

    //Metodo per accettare un voto da parte della segreteria
    @Override
    public boolean accettaVoto(int esame, int matricola) {
        try {
            createOrOpenDB();
            for (int i : map.getKeys()) {
                if (map.get(i).getCodEsame() == esame && map.get(i).getMatricola() == matricola) {
                    map.replace(i, new Sostiene(matricola, esame, map.get(i).nomeCorso, map.get(i).data, map.get(i).ora, map.get(i).voto, true));
                    db.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Err: accetta voto: " + e);
        }
        return false;
    }

    //Metodo per eliminare un oggetto sostiene
    @Override
    public boolean eliminaSostiene(int esame, int matricola) {
        try {
            createOrOpenDB();
            for (int i : map.getKeys()) {
                if (map.get(i).getCodEsame() == esame && map.get(i).getMatricola() == matricola) {
                    map.remove(i);
                    db.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Err: accetta voto: " + e);
        }
        return false;
    }

    @Override
    public Sostiene[] esamiSostenuti() {
        try {
            createOrOpenDB();
            ArrayList<Sostiene> esamiSostenuti = new ArrayList<>();
            for (int i : map.getKeys()) {
                if (!map.get(i).getAccettato() && map.get(i).getVoto() != -1) {
                    esamiSostenuti.add(map.get(i));
                }
            }
            return esamiSostenuti.toArray(new Sostiene[0]);
        } catch (Exception e) {
            System.out.println("Err: accetta voto: " + e);
        }
        return null;
    }

    @Override
    public Esame[] getEsamiSostenibili(int matricola, Corso[] mieiCorsi) {
        try {
            createOrOpenDB();

            //prendo tutti gli esami
            Boolean check;
            Esame[] tuttiEsami = traduciEsame();

            ArrayList<Esame> mieiEsamiCorso = new ArrayList<>();
            ArrayList<Esame> esamiDisponibili = new ArrayList<>();

            HashMap<String, Esame> esami = new HashMap<>();

            for (Esame esame : tuttiEsami) {
                esami.put(esame.nomeCorso, esame);
            }

            ArrayList<Sostiene> mieiEsami = getMieiEsami(matricola);

            // Prendo solo gli esami di un corso in cui lo studente è iscritto
            for(Corso corsi : mieiCorsi){
                for (Esame esame : esami.values()) {
                    if (corsi.getNome().equals(esame.getNomeCorso())) {
                        mieiEsamiCorso.add(esame);
                    }
                }
            }

            // Prendo solo gli esami a cui lo studente non è ancora iscritto
            for (Esame esame : mieiEsamiCorso) {
                check = false;
                for (Sostiene sostiene : mieiEsami) {
                    if (esame.getCodEsame() == sostiene.getCodEsame()) {
                        check = true;
                    }
                }
                if (!check) {
                    esamiDisponibili.add(esame);
                }
            }
            return esamiDisponibili.toArray(new Esame[0]);

        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
    }

    //metodo per prendere i miei esami
    @Override
    public ArrayList<Sostiene> getMieiEsami(int matricola) {
        try {
            createOrOpenDB();
            Sostiene[] sostiene = getSostiene();
            ArrayList<Sostiene> mieiEsami = new ArrayList<>();
            for(Sostiene sostiene1 : sostiene) {
                if (sostiene1.matricola == matricola) {
                    mieiEsami.add(sostiene1);
                }
            }
            return mieiEsami;

        }catch (Exception e){
            System.out.println("Errore: " + e);
            return null;
        }
    }

    //creo una nuova istanza di sostiene
    @Override
    public boolean creaSostiene(int matricola, int codEsame, String nomeCorso, String data, String ora) {
        try {
            createOrOpenDB();
            map.put((map.size() + 1),
                    new Sostiene( matricola, codEsame, nomeCorso, data, ora, -1, false));
            db.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return false;
        }
    }

    @Override
    public long calcolaMedia(Sostiene[] s) {
        int totale = 0;
        for (Sostiene esami : s) {
            totale += esami.voto;
        }
        return totale / s.length;
    }

    public void getEsami(){
        DB dbEsami = getEsamiDB();
        mapEsami = dbEsami.hashMap("esamiMap").counterEnable().keySerializer(Serializer.INTEGER).valueSerializer(new SerializerEsame()).createOrOpen();
    }


}*/