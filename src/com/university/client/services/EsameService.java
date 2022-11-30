package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Corso;
import com.university.client.model.Esame;

@RemoteServiceRelativePath("esame")
public interface EsameService extends RemoteService {
    //Crea un esame
    int creaEsame(String nomeCorso, String data, String ora, String durata, String aula);
    //Modifica un esame
    boolean modificaEsame(int codEsame, String nomeCorso, String data, String ora, String durata, String aula);
    //Elimina un esame
    boolean eliminaEsame(int codEsame);
    //Ricerca un esame per ID(codice Esame)
    Esame getEsame(int codEsame);
    //Restituisce tutti gli esami
    Esame[] getEsami();
    //Restituisce tutti gli esami dei corsi di un docente
    Esame[] getEsamiCorso(Corso[] corsiDocente);
}
