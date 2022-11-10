package com.university.client.services;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Sostiene;

import java.util.ArrayList;

@RemoteServiceRelativePath("sostiene")
public interface SostieneService extends RemoteService {

    Sostiene[] getSostiene();

    ArrayList<Sostiene> getSostieneStudente(int matricola);

    ArrayList<Sostiene> getStudenti(int codEsame);

    boolean creaSostiene(int matricola, int codEsame, int voto);
}