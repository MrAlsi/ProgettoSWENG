package com.university.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("studenti")
public interface StudenteService extends RemoteService {
    String getInformazioniPersonali(String mail);
    void getCorsiDisponibili();
    boolean iscrizioneCorso(String mail);
    void getVoti(String mail);

}
