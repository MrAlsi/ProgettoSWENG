package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Docente;

@RemoteServiceRelativePath("docente")
public interface DocenteService extends RemoteService {
    int getNumeroDocenti();
    Docente[] getDocenti();
    Docente getDocente(int codDocente);
    Boolean eliminaDocente(int codDocente);
    Boolean modificaDocente(String nome, String cognome, String mail, String password, int codDocente);
    Boolean creaDocente(String nome, String cognome, String password);
    Docente loginDocente(String mail, String password);
}
