package com.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.university.client.model.Corso;

public interface CorsoServiceAsync {
    void creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame, AsyncCallback<Boolean> async);


    void modificaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int esame, Corso corso, AsyncCallback<Boolean> async);

    void cercaCorsoByNomeCorso(String nome, AsyncCallback<Corso> async);

    void cercaCorsoByDocente(int docente, AsyncCallback<Corso[]> async);
}
