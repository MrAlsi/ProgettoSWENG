package com.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.university.client.model.Studente;
import com.university.client.model.Utente;

public interface UtenteServiceAsync {
    void getStudenti(AsyncCallback<Studente[]> async);

    void login(String mail, String password, AsyncCallback<Utente> async);
}
