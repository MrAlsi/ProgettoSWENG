package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.*;

import java.util.ArrayList;
import java.util.HashMap;

@RemoteServiceRelativePath("studenti")
public interface StudenteService extends RemoteService {
    String[] getInformazioniPersonali(Studente studente);
    ArrayList<Corso> getCorsiDisponibili(int matricola);
    ArrayList<Sostiene> getVoti(int matricola);
    HashMap<Sostiene, Esame> getEsameSvolti(int matricola);

    ArrayList<Corso> getMieiCorsi(int matricola);

    boolean iscrizioneCorso(Studente s, Corso c);
    boolean iscrizioneEsame(Studente s, Esame e);
}
