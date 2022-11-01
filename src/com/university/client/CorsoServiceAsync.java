package com.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface CorsoServiceAsync {
    void creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame, AsyncCallback<Boolean> async);
    //void creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame, AsyncCallback<Boolean> async);
}
