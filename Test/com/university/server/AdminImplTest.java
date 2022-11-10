package com.university.server;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.university.client.model.Studente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminImplTest extends GWTTestCase {


    DatabaseServiceAsync dbServiceAsync;
    AdminServiceAsync adminServiceAsync;

    @Override
    public String getModuleName() {
        return null;
    }

    public void gwtSetUp(){
        dbServiceAsync = GWT.create(DatabaseService.class);
        adminServiceAsync = GWT.create(AdminService.class);
    }


    @Test
    void getStudenti() {
        delayTestFinish(10000);

        dbServiceAsync.creaDB(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Void result) {
                adminServiceAsync.getStudenti(new AsyncCallback<Studente[]>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        fail("Test fallito: " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(Studente[] result) {
                        assertNotEquals(0, result.length);
                    }
                });
            }
        });

    }

    @Test
    void getDocenti() {
    }

    @Test
    void getSegreteria() {
    }
}