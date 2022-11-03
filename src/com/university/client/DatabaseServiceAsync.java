package com.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Studente;

public interface DatabaseServiceAsync {
    void getStudenti(AsyncCallback<Studente[]> async);
    void creaDB(AsyncCallback<Void> async);
}
