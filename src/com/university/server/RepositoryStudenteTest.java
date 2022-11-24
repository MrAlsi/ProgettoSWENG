package com.university.server;

import com.university.client.model.Studente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepositoryStudenteTest  implements Repository<Studente> {
    //String mail, String password, String dataNascita, int matricola) {

    List<Studente> studentiTest = Arrays.asList(
            new Studente("Gabriel","Alsina","gabriel.alsina@studente.university.com","password","22/08/1999",1),
            new Studente("Carltta","Carboni","carlotta.carboni@studente.university.com","totta","21/02/1999",2)
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
    public Studente[] getAll() {
        return studentiTest.toArray(new Studente[0]);
    }

    @Override
    public boolean Create(Studente object) {
        return true;

    }

    @Override
    public boolean Remove(int id) {
        return true;

    }

    @Override
    public boolean Update(Studente object) {
        return true;

    }
}
