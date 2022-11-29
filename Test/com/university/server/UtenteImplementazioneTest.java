package com.university.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtenteImplementazioneTest {

    UtenteImplementazione implementazioneUtente = new UtenteImplementazione();

    @Test
    void login() {
        assertEquals("Studente", implementazioneUtente.login("studente.studente@studente.university.com"));
        assertEquals("Docente", implementazioneUtente.login("docente.docente@docente.university.com"));
        assertEquals("Segreteria", implementazioneUtente.login("segreteria.segreteria@segreteria.university.com"));
        assertEquals("Admin", implementazioneUtente.login("qualsiasiAltraCosa"));
        assertEquals("Studente", implementazioneUtente.login("sTudEnTe.sTUdeNTe@stUDEnTE.unIVERsitY.COm"));
    }
}