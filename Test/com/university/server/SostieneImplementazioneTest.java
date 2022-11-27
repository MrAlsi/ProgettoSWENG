package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;
import com.university.client.model.Segreteria;
import com.university.client.model.Sostiene;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SostieneImplementazioneTest {
    SostieneImplementazione sostieneImplementazione= new SostieneImplementazione(true);

    List<Sostiene> sostieneTest= Lists.newArrayList(
            new Sostiene(1, 1, "prova", "12/12/2022", "9", -1,false)
    );


    @Test
    void getSostiene() {
    }

    @Test
    void getSostieneStudenteSenzaVoto() {
    }

    @Test
    void getEsamiLibretto() {
    }

    @Test
    void getStudenti() {
    }

    @Test
    void esamiSostenuti() {
    }

    @Test
    void eliminaSostiene() {
    }

    @Test
    void creaSostiene() {
    }

    @Test
    void getEsamiSostenibili() {
    }

    @Test
    void getStudentiInserisciVoto() {
    }

    @Test
    void eliminaEsameSostiene() {
    }
}