package com.university.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UniversityService")
public interface UniversityService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);
    int getNumeroStudenti();

    int[] getDataHP();

/*
    /**
     * Utility/Convenience class.
     * Use UniversityService.App.getInstance() to access static instance of UniversityServiceAsync

    public static class App {
        private static UniversityServiceAsync ourInstance = GWT.create(UniversityService.class);

        public static synchronized UniversityServiceAsync getInstance() {
            return ourInstance;
        }
    }
*/
}
