package com.university.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Docente;

@RemoteServiceRelativePath("docente")
public interface DocenteService extends RemoteService {
    Docente getInfoPersonali(int codDocente);

}
