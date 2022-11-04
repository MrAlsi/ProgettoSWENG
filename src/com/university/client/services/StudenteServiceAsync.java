package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface StudenteServiceAsync {
    void getInformazioniPersonali(Studente studente, AsyncCallback<String[]> async);

    void getVoti(int matricola, AsyncCallback<ArrayList<Sostiene>> async);

    void getCorsiDisponibili(int matricola, AsyncCallback<ArrayList<Corso>> async);

    void getEsameSvolti(int matricola, AsyncCallback<HashMap<Sostiene, Esame>> async);

    void getMieiCorsi(int matricola, AsyncCallback<ArrayList<Frequenta>> async);

    void iscrizioneCorso(Studente s, Corso c, AsyncCallback<Boolean> async);

    void iscrizioneEsame(Studente s, Esame e, AsyncCallback<Boolean> async);
}
