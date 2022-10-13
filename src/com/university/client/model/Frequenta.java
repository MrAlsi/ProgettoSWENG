package com.university.client.model;

import java.io.Serializable;


/**
 * Classe che rappresenta il l'iscrizione di uno studente ad un corso
 */
public class Frequenta implements Serializable {
    public int matricola;
    public int codCorso;

    public Frequenta(){
    }

    public Frequenta(int matricola, int codCorso) {
        this.matricola = matricola;
        this.codCorso = codCorso;
    }

    public int getMatricola() {
        return matricola;
    }

    public int getCodCorso() {
        return codCorso;
    }
}
