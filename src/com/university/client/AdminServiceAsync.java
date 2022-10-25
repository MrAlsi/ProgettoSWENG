package com.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Studente;

public interface AdminServiceAsync {
    void creaStudente(String nome, String cognome, String password, String dataNascita, AsyncCallback<Boolean> async);

    void creaDocente(String nome, String cognome, String password, AsyncCallback<Boolean> async);

    void creaSegreteria(String nome, String cognome, String password, AsyncCallback<Boolean> async);

    void getStudenti(AsyncCallback<Studente[]> async);

    void informazioniStudente(String mail, AsyncCallback<String[]> async);

    void informazioniDocente(String mail, AsyncCallback<String[]> async);

    void getNumeroStudenti(AsyncCallback<Integer> async);
}
