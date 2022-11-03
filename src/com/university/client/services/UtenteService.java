package com.university.client.services;

import com.university.client.model.Studente;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Utente;

@RemoteServiceRelativePath("utenti")
public interface UtenteService extends RemoteService {
    Utente login(String mail, String password);

}
