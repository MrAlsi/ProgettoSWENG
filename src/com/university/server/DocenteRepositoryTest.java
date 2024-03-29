package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Docente;

import java.util.Arrays;
import java.util.List;

public class DocenteRepositoryTest implements RepositoryInt<Docente> {
    List<Docente> docentiTest= Lists.newArrayList(new Docente("Gabriel", "alsina", "gabriel.alsina@docente.university.com", "prova", 1));
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
    public Docente[] getAll() {
        return docentiTest.toArray(new Docente[0]);
    }

    @Override
    public boolean Create(Docente object) {
        try{
            docentiTest.add(object);
            return true;
        } catch(Exception e){
            System.out.println("Create Docente Repository test " + e);
        }
        return false;
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
}
