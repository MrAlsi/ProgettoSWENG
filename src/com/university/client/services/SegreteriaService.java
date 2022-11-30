package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Segreteria;
import com.university.client.model.Studente;

@RemoteServiceRelativePath("segreteria")
public interface SegreteriaService extends RemoteService {
    //Crea oggetto segreteria
    boolean creaSegretaria(String nome, String cognome, String password);
    //Restituisce tutti gli oggetti segreteria
    Segreteria[] getSegreteria();
    //Modifica una segreteria
    boolean modificaSegreteria(String nome, String cognome, String mail, String password);
    //Elimina una segreteria
    boolean eliminaSegreteria(String mail);
    //Ricerca una segreteria in base alla mail
    Segreteria getSegreteria(String mail);
    //Cerca una corrispondenza tra mail e password per il login
    Segreteria loginSegreteria(String mail, String password);
}
