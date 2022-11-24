package com.university.server;

import com.university.client.model.Studente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryStudenteTestTest {
    RepositoryStudenteTest repositoryStudenteTest = new RepositoryStudenteTest();
    StudenteImplementazione studenteImplementazione = new StudenteImplementazione();

    @Test
    void getById() {
        assertEquals(new Studente("Gabriel","Alsina","gabriel.alsina@studente.university.com","password","22/08/1999",1), repositoryStudenteTest.GetById(1));
    }


    @Test
    void getAll() {
    }
}