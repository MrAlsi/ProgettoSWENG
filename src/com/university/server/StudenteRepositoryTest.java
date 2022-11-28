package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;
import com.university.client.model.Studente;

import java.util.Arrays;
import java.util.List;

public class StudenteRepositoryTest implements RepositoryInt<Studente> {
    List<Studente> studentiTest= Lists.newArrayList(
            new Studente("Gabriel","Alsina","gabriel.alsina@studente.university.com","password","22/08/1999",1),
            new Studente("Carlotta","Carboni","carlotta.carboni@studente.university.com","totta","21/02/1999",2),
            new Studente("Alessandro", "Pasi", "alessandro.pasi@studente.university.com", "Pesos", "25/06/1999", 3)
    );

    @Override
    public Studente GetById(int id) {
        for(Studente s: studentiTest){
            if(s.getMatricola() == 1)
                return s;
        }
        return null;
    }

    @Override
    public Studente[] getAll() {
        return studentiTest.toArray(new Studente[0]);
    }

    @Override
    public boolean Create(Studente object) {
        try{
            studentiTest.add(object);
            return true;
        } catch(Exception e){
            System.out.println("Create Studente Repository test " + e);
        }
        return false;
    }

    @Override
    public boolean Remove(int id) {
        try{
            studentiTest.remove(id);
            return true;
        }catch(Exception e){
            System.out.println("StudenteTest " + e);
        }
        return false;
    }
    @Override
    public boolean Update(Studente object) {
        for(Studente s : studentiTest){
            if(s.getMatricola()==object.getMatricola()){
                studentiTest.set(studentiTest.indexOf(s), object);
                studentiTest.remove(s);
                return true;
            }
        }
        return false;
    }
}
