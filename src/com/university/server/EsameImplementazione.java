package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Esame;
import com.university.client.model.Segreteria;
import com.university.client.model.Studente;
import com.university.client.services.EsameService;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EsameImplementazione extends RemoteServiceServlet implements EsameService {

    Repository<Esame> repositoryEsame;
    Boolean singleton = false;
    Boolean test = false;

    public EsameImplementazione(){ }

    //Costruttore per il test
    public EsameImplementazione(Boolean test){
        this.test = test;
        repositoryEsame = new RepositoryEsameTest();
    }

    //Metodo Wrapper...
    public void chiamaDB(){
        // Singleton è un modello di progettazione creazionale che consente di garantire che una classe abbia una
        // sola istanza, fornendo al tempo stesso un punto di accesso globale a questa istanza.
        if(!singleton){
            if(!test){
                ServletContext context = this.getServletContext();
                repositoryEsame = new RepositoryEsame(context);
                singleton = true;
            }
        }
    }

    @Override
    public int creaEsame(String nomeCorso, String data, String ora, String durata, String aula) {
        chiamaDB();
        Esame[] esami = repositoryEsame.getAll();
        repositoryEsame.Create(new Esame((esami.length)+1,nomeCorso,pulisciData(data),ora,durata,aula));
        return (esami.length)+1;
    }

    @Override
    public boolean modificaEsame(int codEsame, String nomeCorso, String data, String ora, String durata, String aula) {
        chiamaDB();
        return repositoryEsame.Update(new Esame(codEsame, nomeCorso, pulisciData(data), ora, durata, aula));

    }

    @Override
    public boolean eliminaEsame(int codEsame) {
        chiamaDB();
        return repositoryEsame.Remove(codEsame);
    }

    @Override
    public Esame getEsame(int codEsame) {
        chiamaDB();
        return repositoryEsame.GetById(codEsame);
    }

    @Override
    public Esame[] getEsami() {
        chiamaDB();
        return repositoryEsame.getAll();
    }

    @Override
    public Esame[] getEsamiCorso(Corso[] corsiDocente) {
        chiamaDB();
        ArrayList<Esame> esami = new ArrayList<>();
        Esame[] tuttiEsami = repositoryEsame.getAll();
        for(Corso corso : Arrays.asList(corsiDocente)){
            for(Esame esame : tuttiEsami){
                if(esame.getCodEsame() == corso.getEsame()){
                    esami.add(esame);
                }
            }
        }
        return esami.toArray(new Esame[0]);
    }

    public String pulisciData(String data){
        if(data.contains("GMT")) {
            String[] date = data.split(" ");
            return date[2] + " " + date[1] + " " + date[5];
        }
        return data;
    }
}
