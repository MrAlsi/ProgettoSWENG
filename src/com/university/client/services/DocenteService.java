package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Docente;

@RemoteServiceRelativePath("docente")
public interface DocenteService extends RemoteService {
    //Restituisce il numero di docenti nel DB
    int getNumeroDocenti();
    //Restituisce tutti i docenti
    Docente[] getDocenti();
    //Restituisce un docente identificato dal codice docente
    Docente getDocente(int codDocente);
    //Elimina docente
    Boolean eliminaDocente(int codDocente);
    //Modifica un docente
    Boolean modificaDocente(String nome, String cognome, String mail, String password, int codDocente);
    //Crea un docente
    Boolean creaDocente(String nome, String cognome, String password);
    //Controlla tutti i docenti per trovare una corrispondenza con mail e password
    Docente loginDocente(String mail, String password);
}
