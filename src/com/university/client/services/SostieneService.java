package com.university.client.services;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Esame;
import com.university.client.model.Sostiene;
import com.university.client.model.Studente;

import java.util.ArrayList;

@RemoteServiceRelativePath("sostiene")
public interface SostieneService extends RemoteService {

    //Restituisce tutti gli oggetti sostiene
    Sostiene[] getSostiene();

    //Restituisce tutti gli oggetti sostiene di un determinato studente senza voto
    Sostiene[] getSostieneStudenteSenzaVoto(int matricola);

    //Restituisce tutti gli oggetti sostiene di un determinato studente con voto
    Sostiene[] getSostieneStudenteConVoto(int matricola);

    //Restituisce tutti gli oggetti sostiene di un determinato studente
    Sostiene[] getStudenti(int codEsame);

    //Aggiungi il voto all'esame sostenuto
    boolean inserisciVoto(int esame, int matricola, int voto);

    //Restituisce tutti gli esami Sostenuti che devono essere accettati
    Sostiene[] esamiSostenuti();

    //Restituisce tutti gli esami che devono ancora essere sostenuti
    Sostiene[] esamiNonSostenuti();

    //Metodo per la segreteria di accettare il voto
    boolean accettaVoto(int esame, int matricola);

    //Metodo per eliminare un oggetto sostiene
    boolean eliminaSostiene(int esame, int matricola);

    //Crea un oggetto sostiene
    boolean creaSostiene(int matricola, int codEsame, int voto);

    //Con l'inserimento di un array di Sostiene restituisce la media aritmentica dell'array
    //è possibile usarlo sia per gli studenti che per i docenti
    long calcolaMedia(Sostiene[] s);
    Esame traduciEsame(int codEsame);
    Studente traduciStudente(int matricola);
}
