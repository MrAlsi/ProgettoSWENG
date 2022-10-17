package com.university.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("admin")
public interface AdminService extends RemoteService {
    boolean creaUtente(String nome, String cognome, String password);
    boolean creaDocente(String nome, String cognome, String password);
    boolean creaSegreteria(String nome, String cognome, String password);
    String informazioniStudente(String mail);
    String informazioniDocente(String mail);
}
