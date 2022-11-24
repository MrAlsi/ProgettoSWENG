package com.university.server;

import com.university.client.model.Studente;
import org.mapdb.HTreeMap;

public class StudenteTest extends StudenteImpl {
    HTreeMap<Integer, Studente> _map;

    public StudenteTest(HTreeMap<Integer, Studente> map){
        _map = map;
    }

    @Override
    public void createOrOpenDB(){
        map = _map;
    }
}
