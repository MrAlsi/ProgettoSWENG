package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Esame;

@RemoteServiceRelativePath("esame")
public interface EsameService extends RemoteService {
    boolean creaEsame(String nomeCorso, String data, String ora, String durata, String aula);
    boolean modificaEsame(int codEsame, String nomeCorso, String data, String ora, String durata, String aula);
    boolean eliminaEsame(int codEsame);
    Esame getEsame(int codEsame);
    Esame[] getEsami();
}
