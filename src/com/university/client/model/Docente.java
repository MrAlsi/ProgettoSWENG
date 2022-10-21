package com.university.client.model;

import java.util.ArrayList;

public class Docente extends Utente{
    public int codDocente;

    public Docente(String nome, String cognome, String mail, String password, int codDocente) {
        super(nome, cognome, mail, password, "Docente");
        this.codDocente = codDocente;
    }

    public Docente() {
    }

    public int getCodDocente() {
        return codDocente;
    }
}
