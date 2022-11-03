package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.services.IndexService;
import com.university.client.model.Corso;
import com.university.client.model.Docente;
import com.university.client.model.Serializer.SerializerCorso;
import com.university.client.model.Serializer.SerializerDocente;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.model.Studente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class IndexImpl extends Database implements IndexService {
    @Override
    public int[] getDataHP(){
        return new int[]{super.getCorsi().length,
                        super.getDocenti().length,
                        super.getStudenti().length};
    }


}
