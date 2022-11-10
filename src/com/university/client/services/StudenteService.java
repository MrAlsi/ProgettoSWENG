package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.*;

import java.util.ArrayList;
import java.util.HashMap;

@RemoteServiceRelativePath("studenti")
public interface StudenteService extends RemoteService {
    int getNumeroStudenti();
    boolean creaStudente(String nome, String cognome, String password, String dataNascita);
    boolean eliminaStudente(int matricola);
    Studente[] getStudenti();
    Studente getStudenteByMatricola(int matricola);
    Studente loginStudente(String mail, String password);
}
