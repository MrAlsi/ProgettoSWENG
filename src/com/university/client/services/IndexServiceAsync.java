package com.university.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface IndexServiceAsync {

   /* void getNumeroCorsi(AsyncCallback<Integer> async);

    void getNumeroDocenti(AsyncCallback<Integer> async);

    void getNumeroStudenti(AsyncCallback<Integer> async);*/

    void getDataHP(AsyncCallback<int[]> async);
}
