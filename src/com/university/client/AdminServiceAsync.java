package com.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface AdminServiceAsync {
    void creaUtente(String nome, String cognome, String password, AsyncCallback<Boolean> async);

    void creaDocente(String nome, String cognome, String password, AsyncCallback<Boolean> async);

    void creaSegreteria(String nome, String cognome, String password, AsyncCallback<Boolean> async);

    void informazioniStudente(String mail, AsyncCallback<String> async);

    void informazioniDocente(String mail, AsyncCallback<String> async);
}
