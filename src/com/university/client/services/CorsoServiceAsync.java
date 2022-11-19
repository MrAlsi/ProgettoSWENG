package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Corso;

public interface CorsoServiceAsync {
    void creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, AsyncCallback<Boolean> async);

    void eliminaCorso(String nome, AsyncCallback<Boolean> async);


    void getCorso(String nome, AsyncCallback<Corso> async);

    void getCorsi(AsyncCallback<Corso[]> async);

    void getNumeroCorsi(AsyncCallback<Integer> async);

    void modificaCorso(String nomeCodice, String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente, int esame, AsyncCallback<Boolean> async);

    void getCorsiDocente(int docente, AsyncCallback<Corso[]> async);
}
