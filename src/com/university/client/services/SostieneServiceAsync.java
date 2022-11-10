package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Sostiene;

import java.util.ArrayList;

public interface SostieneServiceAsync {
    void getSostiene(AsyncCallback<Sostiene[]> async);

    void getSostieneStudente(int matricola, AsyncCallback<ArrayList<Sostiene>> async);

    void getStudenti(int codEsame, AsyncCallback<ArrayList<Sostiene>> async);

    void creaSostiene(int matricola, int codEsame, int voto, AsyncCallback<Boolean> async);
}
