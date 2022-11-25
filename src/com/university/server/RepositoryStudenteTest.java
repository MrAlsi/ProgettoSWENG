package com.university.server;

import com.university.client.model.Studente;

import java.util.Arrays;
import java.util.List;

public class RepositoryStudenteTest implements Repository<Studente> {
    List<Studente> studentiTest = Arrays.asList(
            new Studente("Gabriel","Alsina","gabriel.alsina@studente.university.com","password","22/08/1999",1),
            new Studente("Carltta","Carboni","carlotta.carboni@studente.university.com","totta","21/02/1999",2),
            new Studente("Alessandro", "Pasi", "alessandro.pasi@studente.university.com", "Pesos", "25/06/1999", 3)
            //new Studente("Gabriel","Alsina","gabriel.alsina@studente.university.com","password","22/08/1999",1),

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
    public Studente GetByString(String stringa) {
        return null;
    }

    @Override
    public Studente[] getAll() {
        return studentiTest.toArray(new Studente[0]);
    }

    @Override
    public boolean Create(Studente object) {
        return studentiTest.add(object);
    }

    @Override
    public boolean Remove(int id) {
        try{
            studentiTest.remove(id);
        }catch(Exception e){
            System.out.println("StudenteTest " + e);
        }
        return false;
    }

    @Override
    public boolean RemoveByString(String stringa) {
        return false;
    }

    @Override
    public boolean Update(Studente object) {
        return true;

    }
    @Override
    public boolean UpdateByString(Studente object, String stringa) {
        return false;
    }
}
