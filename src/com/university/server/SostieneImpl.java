package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Docente;
import com.university.client.model.Frequenta;
import com.university.client.model.Serializer.SerializerDocente;
import com.university.client.model.Serializer.SerializerSostiene;
import com.university.client.model.Sostiene;
import com.university.client.services.SostieneService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class SostieneImpl extends RemoteServiceServlet implements SostieneService {

    DB db;
    HTreeMap<Integer, Sostiene> map;

    private DB getDb() {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB) context.getAttribute("sostieneDb");
            if (db == null) {
                db = DBMaker.fileDB("C:\\MapDB\\sostiene").closeOnJvmShutdown().checksumHeaderBypass().make();
                context.setAttribute("sostieneDb", db);
            }
            return db;
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
    @Override
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
    }

    //ottengo i voti di tutti gli studenti che hanno dato l'esame e cui è stato assegnato un voto
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

    // Metodo per aggiungere il voto allo studente
    @Override
    public boolean inserisciVoto(int esame, int matricola, int voto) {
        try {
            createOrOpenDB();
            Sostiene esameConVoto = new Sostiene(esame, matricola, voto, false);
            for (int i : map.getKeys()) {
                if (map.get(i).getCodEsame() == esame && map.get(i).getMatricola() == matricola) {
                    map.replace(i, esameConVoto);
                    db.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Err: inserisci voto: " + e);
        }
        return false;
    }

    @Override
    public boolean accettaVoto(int esame, int matricola) {
        try {
            createOrOpenDB();
            for (int i : map.getKeys()) {
                if (map.get(i).getCodEsame() == esame && map.get(i).getMatricola() == matricola) {
                    map.replace(i, new Sostiene(esame, matricola, map.get(i).voto, true));
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
                if (!map.get(i).getAccettato()) {
                    esamiSostenuti.add(map.get(i));
                }
            }
            return esamiSostenuti.toArray(new Sostiene[0]);
        } catch (Exception e) {
            System.out.println("Err: accetta voto: " + e);
        }
        return null;
    }


    //creo una nuova istanza di sostiene
    @Override
    public boolean creaSostiene(int matricola, int codEsame, int voto) {
        try {
            createOrOpenDB();
            map.put((map.size() + 1),
                    new Sostiene( matricola, codEsame,-1, false));
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
}