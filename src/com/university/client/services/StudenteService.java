package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.*;

@RemoteServiceRelativePath("studenti")
public interface StudenteService extends RemoteService {
    //Restituisce il numero di studenti nel Database
    int getNumeroStudenti();

    //Crea un nuovo studente
    boolean creaStudente(String nome, String cognome, String password, String dataNascita);

    //Modifica i dati di uno studente
    boolean modificaStudente(String nome, String cognome, String mail, String password, String dataNascita, int matricola);

    //Elimina studente
    boolean eliminaStudente(int matricola);

    //Restituisce tutti gli studenti
    Studente[] getStudenti();

    //Cerca uno studente con la matricola
    Studente getStudenteByMatricola(int matricola);

    //controlla le credenziali per effetturare l'accesso
    Studente loginStudente(String mail, String password);
}
