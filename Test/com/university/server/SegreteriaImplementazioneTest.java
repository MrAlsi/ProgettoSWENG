package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Frequenta;
import com.university.client.model.Segreteria;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SegreteriaImplementazioneTest {
    List<Segreteria> segreteriaTest= Lists.newArrayList(
            new Segreteria("Claudia", "Codeluppi", "claudia.codeluppi@segreteria.university.com", "mamma")
    );

    SegreteriaImplementazione implementazione= new SegreteriaImplementazione(true);

    @Test
    void creaSegretaria() {
        assertEquals(true,implementazione.creaSegretaria("carlotta","car","password"));
    }

    @Test
    void getSegreteria() {
        assertArrayEquals(segreteriaTest.toArray(), implementazione.getSegreteria());
    }

    @Test
    void modificaSegreteria() {
    }

    @Test
    void eliminaSegreteria() {
        assertEquals(true,implementazione.eliminaSegreteria("claudia.codeluppi@segreteria.university.com"));
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