package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;
import com.university.client.model.Segreteria;
import com.university.client.model.Sostiene;
import com.university.client.model.Studente;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SostieneImplementazioneTest {
    SostieneImplementazione sostieneImplementazione= new SostieneImplementazione(true);

    List<Sostiene> sostieneTest= Lists.newArrayList(
            new Sostiene(1, 1, "prova", "12/12/2022", "9", -1,false),
            new Sostiene(2, 1, "prova", "12/12/2022", "9", 18,false)

    );

    List <Studente> studenteTest= Lists.newArrayList(
            new Studente("Carlotta","Carboni","carlotta.carboni@studente.university.com","pp","21/02/1999",1)
    );


    @Test
    void getSostiene() {
        assertEquals(sostieneTest.toArray().length, sostieneImplementazione.getSostiene().length);
    }

    @Test
    void getSostieneStudenteSenzaVoto() {
        //assertEquals(sostieneTest.get(0),sostieneImplementazione.getSostieneStudenteSenzaVoto(1));
    }

    @Test
    void getEsamiLibretto() {
    }

    @Test
    void getStudenti() {
        //assertEquals(sostieneTest.get(0),sostieneImplementazione.getStudenti(1));
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