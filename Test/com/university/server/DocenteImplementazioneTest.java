package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;
import com.university.client.model.Docente;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DocenteImplementazioneTest {

    DocenteImplementazione docenteImplementazione= new DocenteImplementazione(true);

    List<Docente> docentiTest= Lists.newArrayList(new Docente("Gabriel", "alsina", "gabriel.alsina@docente.university.com", "prova", 1));

    @Test
    void getDocenti() {
        assertArrayEquals(docentiTest.toArray(),docenteImplementazione.getDocenti());
    }

    @Test
    void getDocente() {
        assertEquals(docentiTest.get(0),docenteImplementazione.getDocente(1));
    }

    @Test
    void eliminaDocente() {
        assertEquals(true,docenteImplementazione.eliminaDocente(1));

    }

    @Test
    void modificaDocente() {
    }

    @Test
    void creaDocente() {
        assertEquals(true,docenteImplementazione.creaDocente("Gabriel", "alsina", "prova"));
    }

    @Test
    void loginDocente() {
        //Controllo login con credenziali giuste
        assertEquals(docentiTest.get(0), docenteImplementazione.loginDocente("gabriel.alsina@docente.university.com", "prova"));
        //Controllo login con credenziali sbagliate
        assertNotEquals(docentiTest.get(0), docenteImplementazione.loginDocente("gabriel.alsina@docente.university.com", "p"));
        assertNotEquals(docentiTest.get(0), docenteImplementazione.loginDocente("gabriel.alsina1@docente.university.com", "prova"));
    }
}