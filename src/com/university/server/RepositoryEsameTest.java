package com.university.server;

import com.university.client.model.Esame;

import java.util.Arrays;
import java.util.List;

public class RepositoryEsameTest implements RepositoryInt<Esame> {

    List<Esame> esamiTest= Arrays.asList(
            new Esame(1,"italiano", "12 12 2022", "9","2", "a" ),
            new Esame(2, "Programmazione", "15 12 2022", "10", "2", "pippo")
    );


    @Override
    public Esame GetById(int id) {
        for (Esame e: esamiTest) {
            if (e.getCodEsame() == id) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Esame GetByString(String stringa) {
        return null;
    }

    @Override
    public Esame[] getAll() {
        return esamiTest.toArray(new Esame[0]);
    }

    @Override
    public boolean Create(Esame object) {
        try{
            esamiTest.add(object);
            return true;
        }catch(Exception e){
            System.out.println("esameTest " + e);
        }
        return false;
    }

    @Override
    public boolean Remove(int id) {
        try{
            esamiTest.remove(id);
        }catch(Exception e){
            System.out.println("esameTest " + e);
        }
        return false;
    }

    @Override
    public boolean RemoveByString(String stringa) {
        return false;
    }

    @Override
    public boolean Update(Esame object) {
        for(Esame e: esamiTest){
            if(e.getCodEsame()==object.getCodEsame()){
                esamiTest.set(esamiTest.indexOf(e),object);
                esamiTest.remove(e);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean UpdateByString(Esame object, String stringa) {
        return false;
    }
}
