package com.university.client.model;

import java.io.Serializable;

public class Sostiene implements Serializable {
    public int matricola;
    public int codEsame;

    public String nomeCorso;

    public String data;
    public String ora;
    public int voto;
    public boolean accettato;

    public Sostiene(int matricola, int codEsame, String nomeCorso, String data, String ora, int voto, boolean accettato) {
        this.matricola = matricola;
        this.codEsame = codEsame;
        this.nomeCorso = nomeCorso;
        this.data = data;
        this.ora = ora;
        this.voto = voto;
        this.accettato = accettato;
    }

    public Sostiene() {
    }

    public int getMatricola() {
        return matricola;
    }

    public int getCodEsame() {
        return codEsame;
    }

    public int getVoto() {
        return voto;
    }

    public boolean getAccettato() {return accettato;}

    public String getData() {
        return data;
    }
    public String getOra() {
        return ora;
    }
    public String getNomeCorso() {
        return nomeCorso;
    }
}
