package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Corso;

@RemoteServiceRelativePath("corso")
public interface CorsoService extends RemoteService {
    //Restituisce il numero dei corsi nel DB
    int getNumeroCorsi();
    //Crea un corso
    Boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente);
    //Restituisce tutti i corsi
    Corso[] getCorsi();
    //Ricerca i dati di un corso attraverso il suo nome
    Corso getCorso(String nome);
    //Restituisce tutti i corsi di un docente
    Corso[] getCorsiDocente (int docente);
    //Elimina un corso
    boolean eliminaCorso(String nome);
    //Modifica un corso
    boolean modificaCorso(String nomeCodice, String nome, String dataInizio, String dataFine, String descrizione, int codocente, int docente, int esame);
}
