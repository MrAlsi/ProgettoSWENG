package com.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface UtenteServiceAsync {
    void creaUtente(String mail, String password, AsyncCallback<Void> async);

    void getStudenti(AsyncCallback<Void> async);
}
