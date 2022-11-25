package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Corso;

@RemoteServiceRelativePath("corso")
public interface CorsoService extends RemoteService {
    int getNumeroCorsi();
    Boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente);
    Corso[] getCorsi();
    Corso getCorso(String nome);
    Corso[] getCorsiDocente (int docente);
    boolean eliminaCorso(String nome);
    boolean modificaCorso(String nomeCodice, String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente, int esame);
}
