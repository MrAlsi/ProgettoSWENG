package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;
import com.university.client.model.Sostiene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SostieneRepositoryTest implements RepositoryDoubleInt<Sostiene> {
    List<Sostiene> sostieneTest= Lists.newArrayList(
            new Sostiene(1, 1, "prova", "12/12/2022", "9", -1,false)
    );

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
        ArrayList<Sostiene> sostieneArrayList = new ArrayList<>();
        for(Sostiene s: sostieneTest){
            if(s.getCodEsame() == id){
                sostieneArrayList.add(s);
            }
        }
        return sostieneArrayList.toArray(new Sostiene[0]);
    }

    @Override
    public boolean Create(Sostiene object) {
        try{
            sostieneTest.add(object);
            return true;
        } catch(Exception e){
            System.out.println("Create Sostiene Repository test " + e);
        }
        return false;
    }

    @Override
    public boolean Remove(int id1, int id2) {
        try{
            for(Sostiene s : sostieneTest){
                if(s.getCodEsame() == id1 && s.getMatricola() == id2){
                    sostieneTest.remove(s);
                    return true;
                }
            }
        }catch(Exception e){
            System.out.println("Create Sostiene Repository test " + e);
        }
        return false;
    }

    @Override
    public boolean Update(Sostiene object, int id1, int id2) {
        for(Sostiene s: sostieneTest){
            if(s.getMatricola() == id1 && s.getCodEsame()==id2){
                sostieneTest.set(sostieneTest.indexOf(s),object);
                sostieneTest.remove(s);
                return true;
            }
        }
        return false;
    }
}
