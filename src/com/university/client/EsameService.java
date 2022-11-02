package com.university.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.university.client.model.Esame;

@RemoteServiceRelativePath("EsameService")
public interface EsameService  extends  RemoteService{
    Esame getEsame(int codEsame);
}
