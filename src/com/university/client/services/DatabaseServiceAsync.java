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

    void creaEsami(int codEsame, String nomeCorso, String data, String ora, String durata, String aula, AsyncCallback<Boolean> async);

    void creaSegratario(String nome, String cognome, String mail, String password, AsyncCallback<Boolean> async);

    void creaSostiene(int matricola, int codEsame, int voto, AsyncCallback<Boolean> async);

    void creaFrequenta(int matricola, String nomeCorso, AsyncCallback<Boolean> async);

    void creaDocenti(String nome, String cognome, String mail, String password, int codDocente, AsyncCallback<Boolean> async);

    void creaStudente(String nome, String cognome, String mail, String password, String dataNascita, int matricola, AsyncCallback<Boolean> async);
}
