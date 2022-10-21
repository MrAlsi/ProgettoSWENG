package com.university.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Frequenta;
import com.university.client.model.Sostiene;
import com.university.client.model.Studente;

import java.util.ArrayList;

@RemoteServiceRelativePath("studenti")
public interface StudenteService extends RemoteService {
    String[] getInformazioniPersonali(Studente studente);
    ArrayList<Frequenta> getCorsiDisponibili(int matricola);
    boolean iscrizioneCorso(String mail);
    ArrayList<Sostiene> getVoti(int matricola);

}
