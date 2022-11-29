package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;

import java.util.Arrays;
import java.util.List;

public class CorsoRepositoryTest implements RepositoryString<Corso> {
    List<Corso> corsiTest= Lists.newArrayList(
            new Corso("italiano", "12/Dec/2022", "1/Mar/2023", "prova", 1,2,1),
            new Corso("Programmazione", "01/Jan/2023", "05/May/2023", "Corso di programmazione", 2, 1, 2));


    @Override
    public Corso GetById(String stringa) {
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
    public boolean Remove(String stringa) {
        try{
            for(Corso c : corsiTest){
                if(c.getNome().equals(stringa)){
                    corsiTest.remove(c);
                    return true;
                }
            }
        }catch(Exception e){
            System.out.println("Create Corso Repository test " + e);
        }
        return false;
    }

    @Override
    public boolean Update(Corso object, String stringa) {
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
