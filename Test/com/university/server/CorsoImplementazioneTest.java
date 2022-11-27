package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CorsoImplementazioneTest {
    CorsoImplementazione corsoImplementazione= new CorsoImplementazione(true);
   List<Corso> corsiTest= Lists.newArrayList(new Corso("italiano", "12/12/2022", "1/2/2023", "prova", 1,2,3));


    //Corso nuovoCorso= new Corso("matematica", "12/12/2022","1/02/2023","prova",1,2,3);

    @Test
    void creaCorso() {
        assertEquals(true, corsoImplementazione.creaCorso("mate", "12/12/2022","1/02/2023","prova",1,2));

    }

    @Test
    void getCorsi() {
        assertEquals(corsiTest.toArray().length,corsoImplementazione.getCorsi().length);
    }

    @Test
    void getCorso() {
        assertEquals(corsiTest.get(0).getNome(), corsoImplementazione.getCorso("italiano").getNome());

    }

    @Test
    void getCorsiDocente() {
        //assertArrayEquals("boh",corsoImplementazione.getCorsiDocente(2));
    }

    @Test
    void eliminaCorso() {
        assertEquals(true,corsoImplementazione.eliminaCorso("italiano"));
    }

    @Test
    void modificaCorso() {
    }
}