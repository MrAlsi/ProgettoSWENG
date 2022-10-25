package com.university.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Studente;

@RemoteServiceRelativePath("admin")
public interface AdminService extends RemoteService {
    boolean creaStudente(String nome, String cognome, String password, String dataNascita);
    boolean creaDocente(String nome, String cognome, String password);
    boolean creaSegreteria(String nome, String cognome, String password);
    String[] informazioniStudente(String mail);
    String[] informazioniDocente(String mail);
    Studente[] getStudenti();
    int getNumeroStudenti();
}
