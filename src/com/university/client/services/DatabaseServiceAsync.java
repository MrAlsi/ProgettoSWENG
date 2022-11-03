package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.*;

public interface DatabaseServiceAsync {
    void getStudenti(AsyncCallback<Studente[]> async);

    void creaDB(AsyncCallback<Void> async);

    void getDocenti(AsyncCallback<Docente[]> async);

    void getSegretari(AsyncCallback<Segreteria[]> async);

    void getCorsi(AsyncCallback<Corso[]> async);

    void getEsami(AsyncCallback<Esame[]> async);

    void getFrequenta(AsyncCallback<Frequenta[]> async);

    void getSostiene(AsyncCallback<Sostiene[]> async);

    void creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame, AsyncCallback<Boolean> async);

    void getAdmin(AsyncCallback<Admin[]> async);
}
