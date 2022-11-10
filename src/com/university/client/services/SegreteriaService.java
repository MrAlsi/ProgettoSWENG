package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Segreteria;

@RemoteServiceRelativePath("segreteria")
public interface SegreteriaService extends RemoteService {
    boolean creaSegretaria(String nome, String cognome, String password);

    Segreteria[] getSegreteria();

    boolean modificaSegreteria(String nome, String cognome, String mail, String password);
    boolean eliminaSegreteria(String mail);
    Segreteria getInformazioni(String mail);

}
