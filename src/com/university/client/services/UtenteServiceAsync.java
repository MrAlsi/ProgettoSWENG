package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.university.client.model.Docente;
import com.university.client.model.Segreteria;
import com.university.client.model.Studente;
import com.university.client.model.Utente;

public interface UtenteServiceAsync {
    void login(String mail, AsyncCallback<String> async);
}
