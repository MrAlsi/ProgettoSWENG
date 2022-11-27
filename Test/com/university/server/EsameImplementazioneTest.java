package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Docente;
import com.university.client.model.Esame;
import com.university.client.model.Studente;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EsameImplementazioneTest {

    EsameImplementazione esameImplementazione= new EsameImplementazione(true);

    List<Esame> esamiTest= Lists.newArrayList(
            new Esame(1,"italiano", "12 12 2022", "9","2", "a" ),
            new Esame(2, "Programmazione", "15 12 2022", "10", "2", "pippo")
    );

    @Test
    void creaEsame() {
        //assertEquals(true,esameImplementazione.creaEsame("Prova", "12 12 2022", "9", "2", "4"));
    }

    @Test
    void modificaEsame() {
    }

    @Test
    void eliminaEsame() {
        assertEquals(true,esameImplementazione.eliminaEsame(1));
    }

    @Test
    void getEsame() {
        assertEquals(esamiTest.get(0).getNomeCorso(),esameImplementazione.getEsame(1).getNomeCorso());
    }

    @Test
    void getEsami() {
       // assertArrayEquals(esamiTest.toArray(),esameImplementazione.getEsami());
        assertEquals(esamiTest.toArray().length, esameImplementazione.getEsami().length);
    }


    @Test
    void getEsamiCorso() {
    }
}