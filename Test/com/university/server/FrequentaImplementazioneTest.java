package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;
import com.university.client.model.Esame;
import com.university.client.model.Frequenta;
import com.university.client.model.Sostiene;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrequentaImplementazioneTest {
    FrequentaImplementazione frequnetaImplementazione= new FrequentaImplementazione(true);

    List<Frequenta> frequentaTest= Lists.newArrayList(
            new Frequenta(1,"italiano" ),
            new Frequenta(2,"matematica")
    );

    List <Corso> corsoTest= Lists.newArrayList(
            new Corso("matematica","12/12/2022","14/12/2022","prova",3,1,2),
            new Corso("italiano","12/12/2022","14/12/2022","prova",3,1,2)

    );

    @Test
    void getFrequenta() {
        assertEquals(1,frequnetaImplementazione.getFrequenta().length);
    }

    @Test
    void getCorsiDisponibili() {
        assertEquals(1,frequnetaImplementazione.getCorsiDisponibili(1).length);
    }

    @Test
    void getMieiCorsi() {
        assertEquals(frequentaTest.get(0).getNomeCorso(),frequnetaImplementazione.getMieiCorsi(1).get(0).getNomeCorso());
    }

    @Test
    void getCorsiStudente() {
        assertEquals(1,frequnetaImplementazione.getCorsiStudente(1).length);
    }

    @Test
    void getStudentiIscritti() {
        assertEquals(frequentaTest.get(0).getMatricola(),frequnetaImplementazione.getStudentiIscritti("italiano").get(0).getMatricola());
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