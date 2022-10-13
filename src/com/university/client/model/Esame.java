package com.university.client.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Esame implements Serializable {

    public int codEsame;
    public String nomeCorso;
    public String data;
    public String ora;
    public String durata;
    public String aula;

    public Esame() {}

    public Esame(int codEsame, String nomeCorso, String data, String ora, String durata, String aula) {
        this.codEsame = codEsame;
        this.nomeCorso = nomeCorso;
        this.data = data;
        this.ora = ora;
        this.durata = durata;
        this.aula = aula;
    }

    //Metodi setter
    public void setData(String data) {
        this.data = data;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    //Metodi getter

    public int getCodEsame() {
        return codEsame;
    }
    public String getData() {
        return data;
    }
    public String getOra() {
        return ora;
    }

    public String getDurata() {
        return durata;
    }
    public String getNomeCorso() {
        return nomeCorso;
    }
    public String getAula() {
        return aula;
    }
}
