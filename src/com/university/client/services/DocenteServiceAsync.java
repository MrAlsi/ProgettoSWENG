package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Docente;

public interface DocenteServiceAsync {
   // void getInfoPersonali(int codDocente, AsyncCallback<Docente> async);

    void getDocenti(AsyncCallback<Docente[]> async);

    void getDocente(int codDocente, AsyncCallback<Docente> async);

    void eliminaDocente(int codDocente, AsyncCallback<Boolean> async);

    void modificaDocente(String nome, String cognome, String mail, String password, int codDocente, AsyncCallback<Boolean> async);

    void creaDocente(String nome, String cognome, String password, int codDocente, AsyncCallback<Boolean> async);
}
