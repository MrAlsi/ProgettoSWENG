package com.university.client.model;

import java.io.Serializable;
import java.util.Date;

public class Studente extends Utente implements Serializable {

    public String dataNascita;
    public int matricola;

    public Studente(String nome, String cognome, String mail, String password, String dataNascita, int matricola) {
        super(nome, cognome, mail, password);
        this.dataNascita = dataNascita;
        this.matricola = matricola;
    }

    public Studente() {
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public int getMatricola() {
        return matricola;
    }
}
