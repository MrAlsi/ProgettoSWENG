package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Docente;
import com.university.client.model.Segreteria;
import com.university.client.model.Studente;

@RemoteServiceRelativePath("admin")
public interface AdminService extends RemoteService {
    //Metodi per Studente
    String[] informazioniStudente(String mail);
    boolean creaStudente(String nome, String cognome, String password, String dataNascita);
    Studente[] getStudenti();

    //Metodi per docente
    boolean creaDocente(String nome, String cognome, String password);
    Docente[] getDocenti();
    String[] informazioniDocente(int codDocente);

    //Metodi per segreteria

}
