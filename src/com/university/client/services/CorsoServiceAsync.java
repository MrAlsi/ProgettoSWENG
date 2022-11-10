package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Corso;

public interface CorsoServiceAsync {
    void creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame, AsyncCallback<Boolean> async);

    void aggiungiCodocente(String nome, int codocente, AsyncCallback<Boolean> async);

    void eliminaEsame(String nome, AsyncCallback<Boolean> async);

    void aggiungiEsame(String nome, int esame, AsyncCallback<Boolean> async);

    void modificaCorso(String nome, String NuovoNome, String dataInizio, String dataFine, String descrizione, int codocente, int esame, AsyncCallback<Boolean> async);

    void eliminaCorso(String nome, AsyncCallback<Boolean> async);

    void getCorsiDocente(int docente, AsyncCallback<Corso[]> async);

    void getMieiCorsi(int matricola, AsyncCallback<Corso[]> async);

    void getCorso(String nome, AsyncCallback<Corso> async);

    void getCorsi(AsyncCallback<Corso[]> async);

    void getNumeroCorsi(AsyncCallback<Integer> async);
}
