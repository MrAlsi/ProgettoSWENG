package com.university.client.services;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.university.client.model.Studente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapdb.HTreeMap;

import java.util.*;

class StudenteServiceTest extends GWTTestCase{
    StudenteServiceAsync service;
    HTreeMap<Integer, Studente> mappa;


    //StudenteTest studenteService = new StudenteTest(mappa);


    @BeforeEach
    public void gwtSetUp(){
        // Create the service that we will test.
        /*
        service = GWT.create(StudenteService.class);
        ServiceDefTarget target = (ServiceDefTarget) service;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "progettosweng/studenti");*/
        ArrayList<Studente> listaStudenti = new ArrayList<Studente>();
        listaStudenti.add(new Studente("Davide", "Gozzi", "davide.gozzi@studente.university.com", "daddydaffy", "01/03/1999", 1));
        Comparator<Studente> c = Comparator.comparingInt(Studente::getMatricola);
        //mappa = listaStudenti.stream().collect(Collectors.groupingBy(Studente::getMatricola, ()->new TreeMap<>(c), Collectors.toSet()));
        //mappa = new DB.HashMapMaker<Integer, Studente>();

    }

    @Override
    public String getModuleName() {
        return "com.university";
    }


    @Test
    public void getNumeroStudenti() throws Exception {
        //delayTestFinish(10000);
        gwtSetUp();
        service.getNumeroStudenti(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("FAIL: getNumeroStudenti");
            }

            @Override
            public void onSuccess(Integer result) {
                assertTrue(true);
            }
        });
    }

    @Test
    void getStudenti() {
    }

    @Test
    void getStudenteByMatricola() {
    }

    @Test
    void loginStudente() {
    }


    @Test
    void testGetNumeroStudenti() {
    }

    @Test
    void testGetStudenteByMatricola() {
    }
}