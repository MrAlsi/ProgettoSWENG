package com.university.server;

import com.university.client.model.Corso;

import java.util.Arrays;
import java.util.List;

public class CorsoRepositoryTest implements Repository<Corso>{
    List<Corso> corsiTest = Arrays.asList(

    );
    @Override
    public Corso GetById(int id) {
        return null;
    }

    @Override
    public Corso GetByString(String stringa) {
        for(Corso c : corsiTest){
            if(c.getNome().equals(stringa)){
                return c;
            }
        }
        return null;
    }

    @Override
    public Corso[] getAll() {
        return corsiTest.toArray(new Corso[0]);
    }

    @Override
    public boolean Create(Corso object) {
        try{
            corsiTest.add(object);
            return true;
        } catch(Exception e){
            System.out.println("Create Corso Repository test " + e);
        }
        return false;
    }

    @Override
    public boolean Remove(int id) {
        return false;
    }

    @Override
    public boolean RemoveByString(String stringa) {
        try{
            for(Corso c : corsiTest){
                if(c.getNome().equals(stringa)){
                    corsiTest.remove(c);
                }
            }
        }catch(Exception e){
            System.out.println("Create Corso Repository test " + e);
        }
        return false;
    }

    @Override
    public boolean Update(Corso object) {
        return false;
    }

    @Override
    public boolean UpdateByString(Corso object, String stringa) {
        try{
            for(Corso c : corsiTest){
                if(c.getNome().equals(stringa)){
                    corsiTest.set(corsiTest.indexOf(c), object);
                    corsiTest.remove(c);
                }
            }
        }catch(Exception e){
            System.out.println("Create Corso Repository test " + e);
        }
        return false;
    }
}
