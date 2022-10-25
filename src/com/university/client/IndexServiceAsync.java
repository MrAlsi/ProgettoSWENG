package com.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface IndexServiceAsync {
    void getDataHP(AsyncCallback<int[]> async);

    void getNumeroStudenti(AsyncCallback<Integer> async);
}
