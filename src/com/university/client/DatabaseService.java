package com.university.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Studente;

@RemoteServiceRelativePath("Database")
public interface DatabaseService extends RemoteService {
    Studente[] getStudenti();
}
