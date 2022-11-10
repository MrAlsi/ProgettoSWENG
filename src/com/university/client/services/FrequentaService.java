package com.university.client.services;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.*;

import java.util.ArrayList;

@RemoteServiceRelativePath("frequenta")
public interface FrequentaService extends RemoteService {

    Frequenta[] getFrequenta ();

    ArrayList<Corso> getCorsiDisponibili (int matricola);

    ArrayList<Frequenta> getMieiCorsi (int matricola);

    ArrayList<Frequenta> getStudentiIscritti (String nomeCorso);
    boolean iscrivi (int matricola, String nomeCorso );
    boolean cancellaIscrizione(int matricola, String nomeCorso);
}
