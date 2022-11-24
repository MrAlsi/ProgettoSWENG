package com.university.server;

import com.university.client.model.Segreteria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegreteriaImplementazioneTest {

    SegreteriaImplementazione implementazione= new SegreteriaImplementazione(true);

    @Test
    void creaSegretaria() {
    }

    @Test
    void getSegreteria() {
    }

    @Test
    void modificaSegreteria() {
    }

    @Test
    void eliminaSegreteria() {
    }

    @Test
    void testGetSegreteria() {
    }

    @Test
    void loginSegreteria() {
        assertEquals(new Segreteria("Claudia", "Codeluppi", "claudia.codeluppi@segreteria.university.com", "mamma"),
                implementazione.loginSegreteria("claudia.codeluppi@segreteria.university.com","mamma"));
        assertNotEquals(new Segreteria("Claudia", "Codeluppi", "claudia.codeluppi@segreteria.university.com", "mamma"),
                implementazione.loginSegreteria("claudia.codeluppi@segreteria.university.com","mammma"));
    }
}