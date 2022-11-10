package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Corso;

import java.util.ArrayList;

@RemoteServiceRelativePath("CorsoService")
public interface CorsoService extends RemoteService {

    int getNumeroCorsi();
    Boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente, int esame);
    Corso[] getCorsi();
    Corso getCorso(String nome);
    Corso[] getMieiCorsi(int matricola);
    Corso[] getCorsiDocente (int docente);
    boolean eliminaCorso(String nome);
    boolean modificaCorso(String nome,String NuovoNome, String dataInizio, String dataFine, String descrizione, int codocente, int esame);
    boolean aggiungiEsame(String nome, int esame);
    boolean eliminaEsame(String nome);
    boolean aggiungiCodocente(String nome, int codocente);
}
