package com.university.server;

import com.university.client.model.Studente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudenteImplementazioneTest {
    StudenteImplementazione studenteImplementazione = new StudenteImplementazione(true);

    @Test
    void getStudenteByMatricola() {
        assertEquals(new Studente("Gabriel","Alsina","gabriel.alsina@studente.university.com","password","22/08/1999",1), studenteImplementazione.getStudenteByMatricola(1));

    }

    @Test
    void chiamaDB() {
    }

    @Test
    void getNumeroStudenti() {
    }

    @Test
    void creaStudente() {
    }

    @Test
    void modificaStudente() {
    }

    @Test
    void eliminaStudente() {
    }

    @Test
    void getStudenti() {
    }

    @Test
    void testGetStudenteByMatricola() {
    }

    @Test
    void loginStudente() {
        //assertEquals(new Studente("Gabriel","Alsina","gabriel.alsina@studente.university.com","password","22/08/1999",1), studenteImplementazione.loginStudente("gabriel.alsina@studente.university.com", "password"));
        assertEquals(null, studenteImplementazione.loginStudente("gaby.it", "paword"));

    }
}