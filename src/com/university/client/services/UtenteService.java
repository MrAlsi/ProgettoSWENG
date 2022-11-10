package com.university.client.services;

import com.university.client.model.Docente;
import com.university.client.model.Segreteria;
import com.university.client.model.Studente;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Utente;

@RemoteServiceRelativePath("utenti")
public interface UtenteService extends RemoteService {

    String login(String mail);

    Studente cercaStudente(String mail, String password);

    Docente cercaDocente(String mail, String password);

    Segreteria cercaSegreteria(String mail, String password);
}
