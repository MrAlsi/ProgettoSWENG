package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;
import com.university.client.model.Segreteria;
import com.university.client.model.Sostiene;
import com.university.client.model.Studente;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SostieneImplementazioneTest {
    SostieneImplementazione sostieneImplementazione= new SostieneImplementazione(true);
    FrequentaImplementazione frequentaImplementazione = new FrequentaImplementazione(true);

    List<Sostiene> sostieneTest= Lists.newArrayList(
            new Sostiene(1, 1, "prova", "12/12/2022", "9", -1,false),
            new Sostiene(2, 1, "prova", "12/12/2022", "9", 18,false)

    );

    @Test
    void getSostiene() {
        assertEquals(5, sostieneImplementazione.getSostiene().length);
    }

    @Test
    void getSostieneStudenteSenzaVoto() {
        assertEquals(2, sostieneImplementazione.getSostieneStudenteSenzaVoto(1).length);
    }

    @Test
    void getEsamiLibretto() {
        assertEquals(1, sostieneImplementazione.getEsamiLibretto(1).length);
    }

    @Test
    void getStudenti() {
        assertEquals(1,sostieneImplementazione.getStudenti(1).length);
    }

    @Test
    void esamiSostenuti() {
        assertEquals(1, sostieneImplementazione.esamiSostenuti().length);
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
        assertEquals(1, sostieneImplementazione.getEsamiSostenibili(1, frequentaImplementazione.getCorsiStudente(1)).length);
    }

    @Test
    void getStudentiInserisciVoto() {
        assertEquals(1, sostieneImplementazione.getStudentiInserisciVoto(2).length);
    }

    @Test
    void eliminaEsameSostiene(){
        assertEquals(true,sostieneImplementazione.eliminaEsameSostiene(1));
    }
}