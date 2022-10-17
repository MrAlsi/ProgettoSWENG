package com.university.client;

import com.university.client.model.Studente;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("utenti")
public interface UtenteService extends RemoteService {
    void creaUtente(String mail, String password);

    void getStudenti();
}
