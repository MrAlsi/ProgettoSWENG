package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.EsameService;
import com.university.client.model.Esame;

public class EsameImpl extends RemoteServiceServlet implements EsameService {
    @Override
    public Esame getEsame(int codEsame) {
        return null;
    }
}
