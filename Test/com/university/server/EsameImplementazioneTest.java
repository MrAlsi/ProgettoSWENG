package com.university.server;

import com.university.client.model.Esame;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EsameImplementazioneTest {

    EsameImplementazione esameImplementazione= new EsameImplementazione(true);

    List<Esame> esamiTest= Arrays.asList(
            new Esame(1,"italiano", "12 12 2022", "9","2", "a" )
    );


    @Test
    void creaEsame() {
        assertEquals(1,esameImplementazione.getEsami().length);
    }

    @Test
    void modificaEsame() {
    }

    @Test
    void eliminaEsame() {
    }

    @Test
    void getEsame() {
    }

    @Test
    void getEsami() {
        assertArrayEquals(esamiTest.toArray(),esameImplementazione.getEsami());
    }


    @Test
    void getEsamiCorso() {
    }
}