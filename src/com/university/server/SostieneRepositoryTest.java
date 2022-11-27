package com.university.server;

import com.university.client.model.Corso;
import com.university.client.model.Sostiene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SostieneRepositoryTest implements RepositoryDoubleInt<Sostiene> {
    List<Sostiene> sostieneTest = Arrays.asList();

    @Override
    public Sostiene[] getAll() {
        return sostieneTest.toArray(new Sostiene[0]);
    }

    @Override
    public Sostiene[] getArrayById1(int id) {
        ArrayList<Sostiene> sostieneArrayList = new ArrayList<>();
        for(Sostiene s:sostieneTest){
            if(s.getMatricola()==id){
                sostieneArrayList.add(s);
            }
        }
        return sostieneArrayList.toArray(new Sostiene[0]);
    }


    @Override
    public Sostiene[] getArrayById2(int id) {
        return new Sostiene[0];
    }

    @Override
    public boolean Create(Sostiene object) {
        return false;
    }

    @Override
    public boolean Remove(int id1, int id2) {
        return false;
    }

    @Override
    public boolean Update(Sostiene object, int id1, int id2) {
        return false;
    }
}
