package com.university.server;

import com.university.client.model.Studente;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminImplTest {

    AdminImpl adminImpl = new AdminImpl();

    @Test
    void creaStudente() {
        assertEquals(true, adminImpl.creaStudente("Gabriel", "Alsina", "password", "22-08-1999") );
        //assertEquals(1, 1);
    }

    @Test
    void getStudenti() {
    }
}