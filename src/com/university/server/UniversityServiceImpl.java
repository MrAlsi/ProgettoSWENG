package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.UniversityService;
import org.mapdb.DB;
import org.mapdb.HTreeMap;

public class UniversityServiceImpl extends RemoteServiceServlet implements UniversityService {
    @Override
    public String getMessage(String msg) {
        return null;
    }
    // Implementation of sample interface method


}