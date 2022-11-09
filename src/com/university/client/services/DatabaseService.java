package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.*;

@RemoteServiceRelativePath("database")
public interface DatabaseService extends RemoteService {
    void creaDB();

    boolean creaEsami(int codEsame, String nomeCorso, String data, String ora, String durata, String aula);

    Frequenta[] getFrequenta();

    boolean creaSegratario(String nome, String cognome, String mail, String password);

    Sostiene[] getSostiene();

    boolean creaSostiene(int matricola, int codEsame, int voto);

    Studente[] getStudenti();

    boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame);

    Docente[] getDocenti();

    boolean creaFrequenta(int matricola, String nomeCorso);

    Segreteria[] getSegretari();

    void studentiDB();

    Admin[] getAdmin();

    Corso[] getCorsi();

    boolean creaDocenti(String nome, String cognome, String mail, String password, int codDocente);

    Esame[] getEsami();

    boolean creaStudente(String nome, String cognome, String mail, String password, String dataNascita, int matricola);
}
