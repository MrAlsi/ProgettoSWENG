package com.university.client.model;

import java.io.Serializable;

public class Sostiene implements Serializable {
    public int matricola;
    public int codEsame;
    public int voto;
    public boolean accettato;

    public Sostiene(int matricola, int codEsame, int voto, boolean accettato) {
        this.matricola = matricola;
        this.codEsame = codEsame;
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
}
