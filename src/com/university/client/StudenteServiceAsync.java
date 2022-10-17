package com.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface StudenteServiceAsync {
    void getInformazioniPersonali(String mail, AsyncCallback<String> async);

    void getCorsiDisponibili(AsyncCallback<Void> async);

    void iscrizioneCorso(String mail, AsyncCallback<Boolean> async);

    void getVoti(String mail, AsyncCallback<Void> async);
}
