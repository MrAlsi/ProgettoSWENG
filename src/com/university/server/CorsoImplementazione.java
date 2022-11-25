package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Segreteria;
import com.university.client.services.CorsoService;
import com.university.client.services.SegreteriaService;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class CorsoImplementazione extends RemoteServiceServlet implements CorsoService {

    Repository<Corso> repositoryCorso;
    Boolean singleton = false;
    Boolean test = false;

    public CorsoImplementazione() {}

    public CorsoImplementazione(Boolean test){
        this.test = test;
        repositoryCorso = new CorsoRepositoryTest();
    }

    //Metodo Wrapper
    public void chiamaDB(){
        // Singleton è un modello di progettazione creazionale che consente di garantire che una classe abbia una
        // sola istanza, fornendo al tempo stesso un punto di accesso globale a questa istanza.
        if(!singleton){
            if(!test){
                ServletContext context = this.getServletContext();
                repositoryCorso = new CorsoRepository(context);
                singleton = true;
            }
        }
    }

    public String pulisciData(String data){
        if(data.contains("GMT")) {
            String[] date = data.split(" ");
            return date[2] + " " + date[1] + " " + date[5];
        }
        return data;
    }

    @Override
    public int getNumeroCorsi() {
        chiamaDB();
        return repositoryCorso.getAll().length;
    }

    @Override
    public Boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente) {
        chiamaDB();
        return repositoryCorso.Create(
                new Corso( nome, pulisciData(dataInizio),
                        pulisciData(dataFine), descrizione,
                        codocente, docente, -1));
    }

    @Override
    public Corso[] getCorsi() {
        chiamaDB();
        return repositoryCorso.getAll();
    }

    @Override
    public Corso getCorso(String nome) {
        chiamaDB();
        return repositoryCorso.GetByString(nome);
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
        return repositoryCorso.RemoveByString(nome);
    }

    @Override
    public boolean modificaCorso(String nomeCodice, String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente, int esame) {
        chiamaDB();
        return repositoryCorso.UpdateByString(
                new Corso(nome, pulisciData(dataInizio), pulisciData(dataFine), descrizione, codocente, docente, esame),
                nomeCodice);
    }
}
