package com.university.client.services;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.*;

@RemoteServiceRelativePath("frequenta")
public interface FrequentaService extends RemoteService {
    //Restituisce tutti gli oggetti frequenta
    Frequenta[] getFrequenta ();
    //Restituisce tutti i corsi disponibili per uno studente, ovvero quelli a cui non è iscritto
    Corso[] getCorsiDisponibili (int matricola);
    //Restitusice gli oggetti frequenta a cui è iscritto uno studente
    Frequenta[] getMieiCorsi (int matricola);
    //Restituisce tutti gli oggetti corso a cui c'è corrispondenza in Frequenta
    Corso[] getCorsiStudente (int matricola);
    //Restituisce tutti gli iscritti a un corso
    Frequenta[] getStudentiIscritti (String nomeCorso);
    //iscrivi uno studente a un corso, in pratica crea un oggetto Frequenta
    boolean iscrivi (int matricola, String nomeCorso );
    //cancella l'iscrizione a un corso, in pratica elimina un oggetto Frequenta
    boolean cancellaIscrizione(int matricola, String nomeCorso);
}
