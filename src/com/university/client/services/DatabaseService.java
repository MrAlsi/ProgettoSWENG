package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.*;

@RemoteServiceRelativePath("database")
public interface DatabaseService extends RemoteService {
    void creaDB();
    Studente[] getStudenti();
    Docente[] getDocenti();
    Segreteria[] getSegretari();
    Corso[] getCorsi();
    Esame[] getEsami();

}
