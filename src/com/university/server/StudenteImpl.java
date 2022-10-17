package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.StudenteService;

public class StudenteImpl extends RemoteServiceServlet implements StudenteService {
    @Override
    public String getInformazioniPersonali(String mail) {
        return null;
    }

    @Override
    public void getCorsiDisponibili() {

    }

    @Override
    public boolean iscrizioneCorso(String mail) {
        return false;
    }

    @Override
    public void getVoti(String mail) {

    }
}
