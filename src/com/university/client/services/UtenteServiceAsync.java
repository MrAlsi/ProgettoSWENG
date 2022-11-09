package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.university.client.model.Docente;
import com.university.client.model.Segreteria;
import com.university.client.model.Studente;
import com.university.client.model.Utente;

public interface UtenteServiceAsync {
    void login(String mail, String password, AsyncCallback<Utente> async);

    void cercaStudente(String mail, String password, AsyncCallback<Studente> async);

    void cercaDocente(String mail, String password, AsyncCallback<Docente> async);

    void cercaSegreteria(String mail, String password, AsyncCallback<Segreteria> async);
}
