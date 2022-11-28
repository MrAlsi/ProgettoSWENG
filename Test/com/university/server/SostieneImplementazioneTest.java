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
        assertEquals(sostieneTest.toArray().length, sostieneImplementazione.getSostiene().length);

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
        assertEquals(true,sostieneImplementazione.eliminaSostiene(1,1));
    }

    @Test
    void creaSostiene() {
        assertEquals(true,sostieneImplementazione.creaSostiene(1,2,"ita","12/12/2022", "9"));
    }

    @Test
    void getEsamiSostenibili() {
    }

    @Test
    void getStudentiInserisciVoto() {
    }

    @Test
    void eliminaEsameSostiene(){
        assertEquals(true,sostieneImplementazione.eliminaEsameSostiene(1));
    }
}