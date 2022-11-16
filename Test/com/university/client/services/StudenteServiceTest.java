package com.university.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudenteServiceTest extends GWTTestCase{
    StudenteServiceAsync service;

    public void gwtSetUp(){
        // Create the service that we will test.
        service = GWT.create(StudenteService.class);
        ServiceDefTarget target = (ServiceDefTarget) service;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "progettosweng/studenti");
    }

    @Override
    public String getModuleName() {
        return "com.university";
    }

    @Test
    public synchronized void getNumeroStudenti() throws Exception {
        delayTestFinish(10000);
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
}