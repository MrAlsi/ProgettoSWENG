package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;
import com.university.client.model.Esame;
import com.university.client.model.Frequenta;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrequentaImplementazioneTest {
    FrequentaImplementazione frequnetaImplementazione= new FrequentaImplementazione(true);

    List<Frequenta> frequentaTest= Lists.newArrayList(
            new Frequenta(1,"italiano" )
    );

    @Test
    void getFrequenta() {
        assertEquals(frequentaTest.toArray().length,frequnetaImplementazione.getFrequenta().length);
    }

    @Test
    void getCorsiDisponibili() {
    }

    @Test
    void getMieiCorsi() {
    }

    @Test
    void getCorsiStudente() {
    }

    @Test
    void getStudentiIscritti() {
    }

    @Test
    void iscrivi() {
        assertEquals(true,frequnetaImplementazione.iscrivi(1,"italiano"));
    }

    @Test
    void cancellaIscrizione() {
        assertEquals(true,frequnetaImplementazione.cancellaIscrizione(1,"italiano"));
    }
}