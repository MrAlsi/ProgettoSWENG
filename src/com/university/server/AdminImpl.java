package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.AdminService;

public class AdminImpl extends RemoteServiceServlet implements AdminService {
    @Override
    public boolean creaUtente(String nome, String cognome, String password) {
        return false;
    }

    @Override
    public boolean creaDocente(String nome, String cognome, String password) {
        return false;
    }

    @Override
    public boolean creaSegreteria(String nome, String cognome, String password) {
        return false;
    }

    @Override
    public String informazioniStudente(String mail) {
        return null;
    }

    @Override
    public String informazioniDocente(String mail) {
        return null;
    }
}
