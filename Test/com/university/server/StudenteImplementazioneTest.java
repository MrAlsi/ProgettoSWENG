package com.university.server;

import com.university.client.model.Studente;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudenteImplementazioneTest {
    StudenteImplementazione studenteImplementazione = new StudenteImplementazione(true);
    List<Studente> studentiTest = Arrays.asList(
            new Studente("Gabriel","Alsina","gabriel.alsina@studente.university.com","password","22/08/1999",1),
            new Studente("Carlotta","Carboni","carlotta.carboni@studente.university.com","totta","21/02/1999",2),
            new Studente("Alessandro", "Pasi", "alessandro.pasi@studente.university.com", "Pesos", "25/06/1999", 3)
    );

    Studente nuovoStudente = new Studente("Nuovo", "Studente", "nuovo.studente@studente.university.com", "nuovo", "01/01/1999", 4);

    @Test
    void getStudenteByMatricola() {
        assertEquals(new Studente("Gabriel", "Alsina", "gabriel.alsina@studente.university.com", "password", "22/08/1999", 1), studenteImplementazione.getStudenteByMatricola(1));
    }

    @Test
    void getNumeroStudenti() {
        assertEquals(studentiTest.size(), studenteImplementazione.getNumeroStudenti());
    }
    @Test
    void getStudenti() {
        assertArrayEquals(studentiTest.toArray(), studenteImplementazione.getStudenti());
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
    void testGetStudenteByMatricola() {
    }

    @Test
    void loginStudente() {
        //Controllo login con credenziali giuste
        assertEquals(studentiTest.get(0), studenteImplementazione.loginStudente("gabriel.alsina@studente.university.com", "password"));
        //Controllo login con credenziali sbagliate
        assertNotEquals(studentiTest.get(0),studenteImplementazione.loginStudente("gabriel.alsina@studente.university.it", "password"));
        assertNotEquals(studentiTest.get(0),studenteImplementazione.loginStudente("gabriel.alsina@studente.university.com", "passwordSbagliata"));

    }
}