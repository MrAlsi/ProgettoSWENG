package com.university.client.model;

import java.io.Serializable;


/**
 * Classe che rappresenta il l'iscrizione di uno studente ad un corso
 */
public class Frequenta implements Serializable {
    public int matricola;
    public String nomeCorso;

    public Frequenta(){
    }

    public Frequenta(int matricola, String nomeCorso) {
        this.matricola = matricola;
        this.nomeCorso = nomeCorso;
    }

    public int getMatricola() {
        return matricola;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }
}
