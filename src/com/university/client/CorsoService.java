package com.university.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Corso;

@RemoteServiceRelativePath("CorsoService")
public interface CorsoService extends RemoteService {

    Corso[] getCorsi();
    boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame);

    //metodi per cercare corsi in base a parameti
    Corso cercaCorsoByNomeCorso(String nome);
    Corso[] cercaCorsoByDocente(int docente);

    boolean modificaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int esame, Corso corso);

}
