package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Esame;

public interface EsameServiceAsync {
    void getEsame(int codEsame, AsyncCallback<Esame> async);
    void getEsami(AsyncCallback<Esame[]> async);
    void creaEsame(String nomeCorso, String data, String ora, String durata, String aula, AsyncCallback<Boolean> async);

    void modificaEsame(int codEsame, String nomeCorso, String data, String ora, String durata, String aula, AsyncCallback<Boolean> async);

    void eliminaEsame(int codEsame, AsyncCallback<Boolean> async);
}
