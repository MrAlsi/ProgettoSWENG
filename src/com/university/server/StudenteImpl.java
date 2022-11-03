package com.university.server;

import com.university.client.model.*;
import com.university.client.services.StudenteService;

import java.util.ArrayList;
import java.util.HashMap;

public class StudenteImpl extends Database implements StudenteService {
    /**
     * Restituisce tutte le informazioni personale di uno studente
     */
    @Override
    public String[] getInformazioniPersonali(Studente studente) {
        try {
            return new String[]{studente.getMail(), studente.getNome(), studente.getCognome(), String.valueOf(studente.getMatricola()), studente.getDataNascita()};
        } catch(Exception e){
            return null;
        }
    }

    /**
     * Restituisce tutti i corsi a cui non è iscritta la matricola
     */
    @Override
    public ArrayList<Frequenta> getCorsiDisponibili(int matricola) {
        return null;
    }

    @Override
    public boolean iscrizioneCorso(String mail) {
        return false;
    }


    public ArrayList<Sostiene> getVoti(int matricola) {
        Sostiene[] sostenuti = super.getSostiene();
        ArrayList<Sostiene> mieiEsami = new ArrayList<>();
        for(Sostiene esame: sostenuti){
            if(esame.matricola==matricola){
                mieiEsami.add(esame);
            }
        }
        return mieiEsami;
    }

    @Override
    public HashMap<Sostiene,Esame> getEsameSvolti(int matricola){
        ArrayList<Sostiene> mieiEsami=getVoti(matricola);
        Esame[] esamiTutti= super.getEsami();
        HashMap<Sostiene,Esame> esamiSostenuti= new HashMap<>();
        for(Esame esame: esamiTutti){
            for(Sostiene sostenuto: mieiEsami){
                if(esame.codEsame==sostenuto.codEsame){
                    esamiSostenuti.put(sostenuto,esame);
                    break;
                }
            }
        }
        return esamiSostenuti;
    }
}
