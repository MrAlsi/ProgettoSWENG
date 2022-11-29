package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.*;
import com.university.client.services.SostieneService;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class SostieneImplementazione extends RemoteServiceServlet implements SostieneService {
    RepositoryDoubleInt<Sostiene> repositorySostiene;
    RepositoryInt<Esame> repositoryEsame;
    RepositoryIntString<Frequenta> repositoryFrequenta;
    RepositoryString<Corso> repositoryCorso;
    Boolean singleton = false;
    Boolean test = false;

    public SostieneImplementazione() {}

    public SostieneImplementazione(Boolean test) {
        this.test = test;
        repositorySostiene= new SostieneRepositoryTest();
        repositoryEsame= new EsameRepositoryTest();
        repositoryFrequenta= new FrequentaRepositoryTest();
        repositoryCorso= new CorsoRepositoryTest();
    }

    //Metodo Wrapper...
    public void chiamaDB(){
        // Singleton è un modello di progettazione creazionale che consente di garantire che una classe abbia una
        // sola istanza, fornendo al tempo stesso un punto di accesso globale a questa istanza.
        if(!singleton){
            if(!test){
                ServletContext context = this.getServletContext();
                repositorySostiene = new SostieneRepository(context);
                repositoryEsame = new EsameRepository(context);
                repositoryFrequenta = new FrequentaRepository(context);
                repositoryCorso = new CorsoRepository(context);
                singleton = true;
            }
        }
    }

    @Override
    public Sostiene[] getSostiene() {
        chiamaDB();
        return repositorySostiene.getAll();
    }

    @Override
    public Sostiene[] getSostieneStudenteSenzaVoto(int matricola) {
        chiamaDB();
        ArrayList<Sostiene> esamiSenzaVoto = new ArrayList<>();
        Sostiene[] esamiStudente = repositorySostiene.getArrayById1(matricola);
        for(Sostiene s : esamiStudente){
            if(s.getVoto()==-1){
                esamiSenzaVoto.add(s);
            }
        }
        return esamiSenzaVoto.toArray(new Sostiene[0]);
    }

    @Override
    public Sostiene[] getEsamiLibretto(int matricola) {
        chiamaDB();
        ArrayList<Sostiene> esamiSenzaVoto = new ArrayList<>();
        Sostiene[] esamiStudente = repositorySostiene.getArrayById1(matricola);
        for(Sostiene s : esamiStudente){
            if(s.getAccettato()){
                esamiSenzaVoto.add(s);
            }
        }
        return esamiSenzaVoto.toArray(new Sostiene[0]);
    }

    //ottengo i voti di tutti gli studenti che hanno dato l'esame e cui è stato assegnato un voto
    @Override
    public Sostiene[] getStudenti(int codEsame) {
        chiamaDB();
        return repositorySostiene.getArrayById2(codEsame);
    }

    // Metodo per aggiungere il voto allo studente
    @Override
    public boolean inserisciVoto(int esame, int matricola, int voto) {
        chiamaDB();
        for(Sostiene s : repositorySostiene.getAll()){
            if(s.getCodEsame()==esame && s.getMatricola()==matricola){
                return repositorySostiene.Update(
                        new Sostiene(matricola, esame, s.getNomeCorso(), s.getData(), s.getOra(), voto, false),
                        matricola, esame);
            }
        }
        return false;
    }

    //Metodo per avere tutti gli esami a cui manca l'accettazione della segreteria
    @Override
    public Sostiene[] esamiSostenuti() {
        chiamaDB();
        ArrayList<Sostiene> esamiSostenuti = new ArrayList<>();
        for(Sostiene s : repositorySostiene.getAll()){
            if(!s.getAccettato() && s.getVoto()!=-1){
                esamiSostenuti.add(s);
            }
        }
        return esamiSostenuti.toArray(new Sostiene[0]);
    }

    //Metodo per accettare un voto da parte della segreteria
    @Override
    public boolean accettaVoto(int esame, int matricola) {
        chiamaDB();
        for(Sostiene s : repositorySostiene.getAll()){
            if(s.getCodEsame()==esame && s.getMatricola()==matricola){
                return repositorySostiene.Update(
                        new Sostiene(matricola, esame, s.getNomeCorso(), s.getData(), s.getOra(), s.getVoto(), true),
                        matricola, esame);
            }
        }
        return false;
    }

    @Override
    public boolean eliminaSostiene(int esame, int matricola) {
        chiamaDB();
        return repositorySostiene.Remove(esame, matricola);
    }

    @Override
    public boolean creaSostiene(int matricola, int codEsame, String nomeCorso, String data, String ora) {
        chiamaDB();
        return repositorySostiene.Create(new Sostiene(matricola, codEsame, nomeCorso, data, ora, -1, false));
    }

    @Override
    public Esame[] getEsamiSostenibili(int matricola, Corso[] mieiCorsi) {
        chiamaDB();
        ArrayList<Esame> esamiSostenibili = new ArrayList();
        for(Frequenta f : repositoryFrequenta.getArrayByInt(matricola)){
            boolean sostenuto = false;
            for(Sostiene s : repositorySostiene.getArrayById1(matricola)){
                if(f.getNomeCorso().equals(s.getNomeCorso())){
                    sostenuto = true;
                    break;
                }
            }
            if(!sostenuto){
                esamiSostenibili.add(traduciEsame(f));
            }
        }

        return esamiSostenibili.toArray(new Esame[0]);
    }

    @Override
    public Sostiene[] getStudentiInserisciVoto(int codEsame) {
        ArrayList<Sostiene> sostenuti= new ArrayList<>();
        for(Sostiene s: repositorySostiene.getArrayById2(codEsame)){
            if(s.getVoto()==-1){
                sostenuti.add(s);
            }
        }
        return sostenuti.toArray(new Sostiene[0]);
    }
    public boolean eliminaEsameSostiene(int esame){
        chiamaDB();
        for(Sostiene s : repositorySostiene.getArrayById2(esame)){
            repositorySostiene.Remove(s.matricola, esame);
        }
        return true;
    }

    public Esame traduciEsame(Frequenta f){
        chiamaDB();
        for(Esame e : repositoryEsame.getAll()){
            if(e.getNomeCorso().equals(f.getNomeCorso())){
                return e;
            }
        }
        return null;
    }

}
