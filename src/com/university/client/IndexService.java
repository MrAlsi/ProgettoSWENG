package com.university.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("index")
public interface IndexService extends RemoteService {
    int[] getDataHP();
    int getNumeroStudenti();
}
