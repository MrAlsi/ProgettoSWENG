package com.university.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniversityServiceImplTest {
    UniversityServiceImpl uniService = new UniversityServiceImpl();
    @Test
    void getNumeroCorsi() {
        assertNotEquals(-1, uniService.getNumeroCorsi());
    }

    @Test
    void getNumeroDocenti() {
        assertNotEquals(-1, uniService.getNumeroDocenti());
    }

    @Test
    void getNumeroStudenti() {
        assertNotEquals(-1, uniService.getNumeroStudenti());
    }
}