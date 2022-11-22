package com.university.client.model;

import java.io.Serializable;
import java.util.Date;

public class Corso implements Serializable {
    public String nome;
    public String dataInizio;
    public String dataFine;
    public String descrizione;
    public int docente;
    public int coDocente;
    public int esame;


    public Corso() {
    }

    public Corso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame) {
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.descrizione = descrizione;
        this.docente = docente;
        this.coDocente = coDocente;
        this.esame = esame;
    }

    public String getNome() {
        return nome;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getDocente() {
        return docente;
    }

    public int getCoDocente() {
        return coDocente;
    }

    public int getEsame() {
        return esame;
    }

    public void setEsame(int codEsame){
        this.esame = codEsame;
    }
}
