package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Esame;
import com.university.client.model.Sostiene;
import com.university.client.model.Studente;

import java.util.ArrayList;

public interface SostieneServiceAsync {
    void getSostiene(AsyncCallback<Sostiene[]> async);
    void creaSostiene(int matricola, int codEsame, int voto, AsyncCallback<Boolean> async);

    void getStudenti(int codEsame, AsyncCallback<Sostiene[]> async);

    void esamiSostenuti(AsyncCallback<Sostiene[]> async);

    void inserisciVoto(int esame, int matricola, int voto, AsyncCallback<Boolean> async);

    void accettaVoto(int esame, int matricola, AsyncCallback<Boolean> async);

    void calcolaMedia(Sostiene[] s, AsyncCallback<Long> async);

    //Restituisce tutti gli oggetti sostiene di un determinato studente senza voto
    void getSostieneStudenteSenzaVoto(int matricola, AsyncCallback<Sostiene[]> async);

    //Restituisce tutti gli oggetti sostiene di un determinato studente con voto
    void getSostieneStudenteConVoto(int matricola, AsyncCallback<Sostiene[]> async);

    //Metodo per eliminare un oggetto sostiene
    void eliminaSostiene(int esame, int matricola, AsyncCallback<Boolean> async);

    void traduciEsame(int codEsame, AsyncCallback<Esame> async);

    void traduciStudente(int matricola, AsyncCallback<Studente> async);
}
