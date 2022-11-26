package com.university.client.services;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Esame;
import com.university.client.model.*;
import com.university.client.model.Studente;

import java.util.ArrayList;

@RemoteServiceRelativePath("sostiene")
public interface SostieneService extends RemoteService {

    //Restituisce tutti gli oggetti sostiene
    Sostiene[] getSostiene();

    //Restituisce tutti gli oggetti sostiene di un determinato studente senza voto
    Sostiene[] getSostieneStudenteSenzaVoto(int matricola);

    //Restituisce tutti gli oggetti sostiene di un determinato studente con voto
    //Sostiene[] getSostieneStudenteConVoto(int matricola);

    //ottengo i voti dello studente
    Sostiene[] getEsamiLibretto(int matricola);

    //Restituisce tutti gli oggetti sostiene di un determinato studente
    Sostiene[] getStudenti(int codEsame);

    //Aggiungi il voto all'esame sostenuto
    boolean inserisciVoto(int esame, int matricola, int voto);

    //Restituisce tutti gli esami Sostenuti che devono essere accettati
    Sostiene[] esamiSostenuti();

    //Metodo per la segreteria di accettare il voto
    boolean accettaVoto(int esame, int matricola);

    //metodo per eliminare tutti i sostiene con un codice esame
    boolean eliminaEsameSostiene(int esame);

    //Metodo per eliminare un oggetto sostiene
    boolean eliminaSostiene(int esame, int matricola);

    //Crea un oggetto sostiene
    boolean creaSostiene(int matricola, int codEsame, String nomeCorso, String data, String ora);

    // restituisce tutti gli esami sostenibili dallo studente
    Esame[] getEsamiSostenibili(int matricola, Corso[] mieiCorsi);

    Sostiene[] getStudentiInserisciVoto(int codEsame);

    ArrayList<Sostiene> getMieiEsami(int matricola);
}
