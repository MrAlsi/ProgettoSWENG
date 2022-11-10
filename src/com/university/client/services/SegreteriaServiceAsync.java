package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Segreteria;

public interface SegreteriaServiceAsync {
    void creaSegretaria(String nome, String cognome, String password, AsyncCallback<Boolean> async);

    void modificaSegreteria(String nome, String cognome, String mail, String password, AsyncCallback<Boolean> async);

    void eliminaSegreteria(String mail, AsyncCallback<Boolean> async);

    void getSegreteria(AsyncCallback<Segreteria[]> async);

    void getSegreteria(String mail, AsyncCallback<Segreteria> async);
}
