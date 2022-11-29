package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Esame;
import com.university.client.model.Frequenta;
import com.university.client.services.CorsoService;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class CorsoImplementazione extends RemoteServiceServlet implements CorsoService {

    RepositoryString<Corso> repositoryCorso;
    RepositoryInt<Esame> repositoryEsame;
    RepositoryIntString<Frequenta> repositoryFrequenta;
    Boolean singleton = false;
    Boolean test = false;

    public CorsoImplementazione() {}

    public CorsoImplementazione(Boolean test){
        this.test = test;
        repositoryCorso = new CorsoRepositoryTest();
        repositoryEsame= new EsameRepositoryTest();
        repositoryFrequenta= new FrequentaRepositoryTest();
    }

    //Metodo Wrapper
    public void chiamaDB(){
        // Singleton è un modello di progettazione creazionale che consente di garantire che una classe abbia una
        // sola istanza, fornendo al tempo stesso un punto di accesso globale a questa istanza.
        if(!singleton){
            if(!test){
                ServletContext context = this.getServletContext();
                repositoryCorso = new CorsoRepository(context);
                repositoryEsame = new EsameRepository(context);
                repositoryFrequenta = new FrequentaRepository(context);
                singleton = true;
            }
        }
    }

    @Override
    public int getNumeroCorsi() {
        chiamaDB();
        return repositoryCorso.getAll().length;
    }

    @Override
    public Boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente) {
        chiamaDB();
        if(controlloCorso(nome)==null){
            return repositoryCorso.Create(new Corso( nome, pulisciData(dataInizio),
            pulisciData(dataFine), descrizione, codocente, docente, -1));
        }
        return false;
    }

    public Corso controlloCorso(String nome){
        chiamaDB();
        return repositoryCorso.GetById(nome);
    }

    @Override
    public Corso[] getCorsi() {
        chiamaDB();
        return repositoryCorso.getAll();
    }

    @Override
    public Corso getCorso(String nome) {
        chiamaDB();
        return repositoryCorso.GetById(nome);
    }

    @Override
    public Corso[] getCorsiDocente(int docente) {
        chiamaDB();
        ArrayList<Corso> corsi = new ArrayList<>();
        for(Corso c : repositoryCorso.getAll()){
            if(c.getDocente()==docente){
                corsi.add(c);
            }
        }
        return corsi.toArray(new Corso[0]);
    }

    @Override
    public boolean eliminaCorso(String nome) {
        chiamaDB();
        for(Esame e : repositoryEsame.getAll()){
            if(e.getNomeCorso().equals(nome)){
                repositoryEsame.Remove(e.getCodEsame());
                break;
            }
        }
        for(Frequenta f : repositoryFrequenta.getAll()){
            if(f.getNomeCorso().equals(nome)){
                repositoryFrequenta.Remove(f.getMatricola(), f.getNomeCorso());
            }
        }

        return repositoryCorso.Remove(nome);
    }

    @Override
    public boolean modificaCorso(String nomeCodice, String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente, int esame) {
        chiamaDB();
        return repositoryCorso.Update(
                new Corso(nome, pulisciData(dataInizio), pulisciData(dataFine), descrizione, codocente, docente, esame),
                nomeCodice);
    }

    public String pulisciData(String data){
        if(data.contains("GMT")) {
            String[] date = data.split(" ");
            return date[2] + " " + date[1] + " " + date[5];
        }
        return data;
    }

}
