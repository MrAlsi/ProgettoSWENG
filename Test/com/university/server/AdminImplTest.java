package com.university.server;

import com.university.client.model.Admin;
import com.university.client.model.Studente;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminImplTest {

    AdminImpl adminImpl = new AdminImpl();

    @Test
    void creaStudente() {
        assertEquals(true, adminImpl.creaStudente("Gabriel", "Alsina", "password", "22-08-1999"));
    }

    @Test
    void getStudenti() {
        assertNotEquals(null, adminImpl.getStudenti());
    }

    @Test
    void creaMailStudente() {
        assertEquals("test.test@studente.university.com", adminImpl.creaMailStudente("test", "test"));
    }

    @Test
    void informazioniStudente() {
    }

    @Test
    void creaDocente() {
    }

    @Test
    void getMailDocente() {
    }

    @Test
    void getDocenti() {
    }

    @Test
    void informazioniDocente() {
    }

    @Test
    void creaSegreteria() {
    }
}