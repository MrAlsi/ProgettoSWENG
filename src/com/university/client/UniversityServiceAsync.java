package com.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UniversityServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
    void getNumeroStudenti(AsyncCallback<Integer> async);
    void getDataHP(AsyncCallback<int[]> async);
}
