package com.university.server;

import com.university.client.model.Docente;

import java.util.Arrays;
import java.util.List;

public class DocenteRepositoryTest implements Repository<Docente>{
    List<Docente> docentiTest = Arrays.asList(

    );

    @Override
    public Docente GetById(int id) {
        for(Docente d : docentiTest){
            if(d.getCodDocente() == id){
                return d;
            }
        }
        return null;
    }

    @Override
    public Docente GetByString(String stringa) {
        return null;
    }

    @Override
    public Docente[] getAll() {
        return docentiTest.toArray(new Docente[0]);
    }

    @Override
    public boolean Create(Docente object) {
        return docentiTest.add(object);
    }

    @Override
    public boolean Remove(int id) {
        for(Docente d : docentiTest){
            if(d.getCodDocente() == id){
                return docentiTest.remove(d);
            }
        }
        return false;
    }

    @Override
    public boolean RemoveByString(String stringa) {
        return false;
    }

    @Override
    public boolean Update(Docente object) {
        for (Docente d : docentiTest) {
            if(d.getCodDocente() == object.getCodDocente()){
                docentiTest.set(docentiTest.indexOf(d), object);
                docentiTest.remove(d);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean UpdateByString(Docente object, String stringa) {
        return false;
    }
}
