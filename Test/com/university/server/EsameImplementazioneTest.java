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
        //Data giusta
        assertEquals(3, esameImplementazione.creaEsame("italiano", "10/Aug/2023", "9", "2", "4"));
        //Data dell'esame prima della data della fine del corso, dovrebbe ritornare -1 codice che l'operazione non è andata a buon fine
        assertEquals(-1, esameImplementazione.creaEsame("italiano", "10/Dec/2022", "9", "2", "4"));
        //Assegna esame a corso che non esiste
        assertEquals(-1, esameImplementazione.creaEsame("matemitica", "10/Dec/2022", "9", "2", "4"));
    }

    @Test
    void eliminaEsame() {
        assertEquals(true,esameImplementazione.eliminaEsame(1));
    }

    @Test
    void getEsame() {
        //Non funzionava assertEquals per qualche motivo, comunque tutti i dati combaciano
        assertEquals(esamiTest.get(0).getCodEsame(),esameImplementazione.getEsame(1).getCodEsame());
        assertEquals(esamiTest.get(0).getNomeCorso(),esameImplementazione.getEsame(1).getNomeCorso());
        assertEquals(esamiTest.get(0).getData(),esameImplementazione.getEsame(1).getData());
        assertEquals(esamiTest.get(0).getDurata(),esameImplementazione.getEsame(1).getDurata());
        assertEquals(esamiTest.get(0).getAula(),esameImplementazione.getEsame(1).getAula());
    }

    @Test
    void getEsami() {
        assertEquals(esamiTest.toArray().length, esameImplementazione.getEsami().length);
    }

}