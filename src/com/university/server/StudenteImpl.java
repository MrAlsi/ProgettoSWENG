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
    public ArrayList<Corso> getCorsiDisponibili(int matricola) {
        Corso [] tuttiCorsi= super.getCorsi();
        ArrayList<Corso> corsiDisponibili= new ArrayList<>();
        HashMap<String,Corso> corsi=new HashMap<>();
        for(Corso corso: tuttiCorsi){
            corsi.put(corso.nome,corso);
        }
        ArrayList<Frequenta> mieiCorsi=getMieiCorsi(matricola);
        for(Frequenta frequenta: mieiCorsi){
            if(!corsi.containsKey(frequenta.nomeCorso)){
                corsiDisponibili.add(corsi.get(frequenta.nomeCorso));
            }
        }
        return corsiDisponibili;
    }

    @Override
    public ArrayList<Frequenta> getMieiCorsi(int matricola) {
        Frequenta[] frequenta = super.getFrequenta();
        ArrayList<Frequenta> mieiCorsi = new ArrayList<>();
        for(Frequenta corso: frequenta){
            if(corso.matricola==matricola){
                mieiCorsi.add(corso);
            }
        }
        return mieiCorsi;
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

    /**
     * Iscrive uno studente ad un corso
     */
    @Override
    public boolean iscrizioneCorso(Studente s, Corso c) {
        try {
            super.creaFrequenta(s.matricola, c.nome);
            return true;
        } catch(Exception e){
            System.out.println("C: StudenteImpl  M: iscrizioneCorso: " + e);
            return false;
        }
    }

    @Override
    public boolean iscrizioneEsame(Studente s, Esame e){
        try{
            super.creaSostiene(s.matricola, e.codEsame, -1);
            return true;
        } catch(Exception ex){
            System.out.println("C: StudenteImpl  M: iscrizioneEsame: " + ex);
            return false;
        }
    }
}
