package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Esame;
import com.university.client.model.Sostiene;
import com.university.client.services.EsameService;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class EsameImplementazione extends RemoteServiceServlet implements EsameService {
    RepositoryInt<Esame> repositoryEsame;
    RepositoryString<Corso> repositoryCorso;
    RepositoryDoubleInt<Sostiene> repositorySostiene;
    Boolean singleton = false;
    Boolean test = false;

    public EsameImplementazione(){ }

    //Costruttore per il test
    public EsameImplementazione(Boolean test){
        this.test = test;
        repositoryEsame = new EsameRepositoryTest();
        repositoryCorso= new CorsoRepositoryTest();
        repositorySostiene= new SostieneRepositoryTest();
    }

    //Metodo Wrapper...
    public void chiamaDB(){
        // Singleton è un modello di progettazione creazionale che consente di garantire che una classe abbia una
        // sola istanza, fornendo al tempo stesso un punto di accesso globale a questa istanza.
        if(!singleton){
            if(!test){
                ServletContext context = this.getServletContext();
                repositoryEsame = new EsameRepository(context);
                repositoryCorso= new CorsoRepository(context);
                repositorySostiene = new SostieneRepository(context);
                singleton = true;
            }
        }
    }

    @Override
    public int creaEsame(String nomeCorso, String data, String ora, String durata, String aula) {
        chiamaDB();
        try {
            Esame[] esami = repositoryEsame.getAll();
            if (!controllaDate(data, repositoryCorso.GetById(nomeCorso).dataFine)) {
                repositoryEsame.Create(new Esame((esami.length) + 1, nomeCorso, pulisciData(data), ora, durata, aula));
                return (esami.length) + 1;
            }
        }  catch (NullPointerException e){}
           return -1;
    }

    @Override
    public boolean modificaEsame(int codEsame, String nomeCorso, String data, String ora, String durata, String aula) {
        chiamaDB();
        return repositoryEsame.Update(new Esame(codEsame, nomeCorso, pulisciData(data), ora, durata, aula));

    }

    @Override
    public boolean eliminaEsame(int codEsame) {
        chiamaDB();
        for(Sostiene s : repositorySostiene.getAll()){
            if(s.getCodEsame()==codEsame){
                repositorySostiene.Remove(s.getCodEsame(), s.getMatricola());
            }
        }
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

    public  Boolean controllaDate(String dataEsame, String dataCorso){
        String[] prima = stringToDate(dataEsame).split("/");
        String[] dopo = stringToDate(dataCorso).split("/");
        if(Integer.parseInt(prima[2])<Integer.parseInt(dopo[2])) {
            return true;
        } else if(Integer.parseInt(prima[2]) == Integer.parseInt(dopo[2])){
            if(Integer.parseInt(prima[1])<Integer.parseInt(dopo[1])) {
                return true;
            } else if(Integer.parseInt(prima[1])==Integer.parseInt(dopo[1])){
                if(Integer.parseInt(prima[0])<Integer.parseInt(dopo[0])){
                    return true;
                }
            }
        }
        return false;
    }

    public String stringToDate (String data){
        String[] amg;
        if(data.contains("/")){
            amg = data.split("/");
        } else {
            amg = data.split(" ");
        }
        int mese;
        //Window.alert(amg[2]);
        switch(amg[1]){
            case "Jan": mese = 0; break;
            case "Feb": mese = 1; break;
            case "Mar": mese = 2; break;
            case "Apr": mese = 3; break;
            case "May": mese = 4; break;
            case "Jun": mese = 5; break;
            case "Jul": mese = 6; break;
            case "Aug": mese = 7; break;
            case "Sep": mese = 8; break;
            case "Oct": mese = 9; break;
            case "Nov": mese = 10; break;
            default: mese = 11; break;
        }
        if(amg.length==3){
            return amg[0]+"/"+mese+"/"+amg[2];
        } else {
            return amg[2]+"/"+mese+"/"+amg[5];
        }
    }
}
