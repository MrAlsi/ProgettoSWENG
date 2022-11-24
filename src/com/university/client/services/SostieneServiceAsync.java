package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Esame;
import com.university.client.model.*;
import com.university.client.model.Studente;

import java.util.ArrayList;

public interface SostieneServiceAsync {
    void getSostiene(AsyncCallback<Sostiene[]> async);
    void creaSostiene(int matricola, int codEsame, String nomeCorso, String data, String ora, AsyncCallback<Boolean> async);

    void getStudenti(int codEsame, AsyncCallback<Sostiene[]> async);

    void esamiSostenuti(AsyncCallback<Sostiene[]> async);

    void inserisciVoto(int esame, int matricola, int voto, AsyncCallback<Boolean> async);

    void accettaVoto(int esame, int matricola, AsyncCallback<Boolean> async);

    void calcolaMedia(Sostiene[] s, AsyncCallback<Long> async);

    //Restituisce tutti gli oggetti sostiene di un determinato studente senza voto
    void getSostieneStudenteSenzaVoto(int matricola, AsyncCallback<Sostiene[]> async);

    //Restituisce tutti gli oggetti sostiene di un determinato studente con voto
    void getSostieneStudenteConVoto(int matricola, AsyncCallback<Sostiene[]> async);

    //ottengo i voti dello studente
    void getEsamiLibretto(int matricola, AsyncCallback<Sostiene[]> async);

    //Metodo per eliminare un oggetto sostiene
    void eliminaSostiene(int esame, int matricola, AsyncCallback<Boolean> async);

    void traduciEsame(int codEsame, AsyncCallback<Esame> async);

    void getEsamiSostenibili(int matricola, Corso[] mieiCorsi, AsyncCallback<Esame[]> async);

    void getMieiEsami(int matricola, AsyncCallback<ArrayList<Sostiene>> async);

    void getStudentiInserisciVoto(int codEsame, AsyncCallback<Sostiene[]> async);
}
