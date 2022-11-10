package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.*;

import java.util.ArrayList;

public interface FrequentaServiceAsync {

    void getMieiCorsi(int matricola, AsyncCallback<ArrayList<Frequenta>> async);

    void getCorsiDisponibili(int matricola, AsyncCallback<ArrayList<Corso>> async);

    void getFrequenta(AsyncCallback<Frequenta[]> async);

    void getStudentiIscritti(String nomeCorso, AsyncCallback<ArrayList<Frequenta>> async);

    void iscrivi(int matricola, String nomeCorso, AsyncCallback<Boolean> async);

    void cancellaIscrizione(int matricola, String nomeCorso, AsyncCallback<Boolean> async);
}