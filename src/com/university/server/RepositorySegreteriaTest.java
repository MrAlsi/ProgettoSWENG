package com.university.server;

import com.university.client.model.Segreteria;

import java.util.Arrays;
import java.util.List;

public class RepositorySegreteriaTest implements Repository<Segreteria> {

    List<Segreteria> segreteriaTest = Arrays.asList(
            new Segreteria("Claudia", "Codeluppi", "claudia.codeluppi@segreteria.university.com", "mamma")
    );
    @Override
    public Segreteria GetById(int id) {
        return null;
    }

    @Override
    public Segreteria GetByString(String stringa) {
        try{
            for(Segreteria s: segreteriaTest){
                if(s.getMail().equals(stringa)){
                    return s;
                }
            }
        } catch(Exception e){
            System.out.println("Err: getSegreteria  " + e);
        }
        return null;
    }

    @Override
    public Segreteria[] getAll() {
        return segreteriaTest.toArray(new Segreteria[0]);
    }

    @Override
    public boolean Create(Segreteria object) {
        return false;
    }

    @Override
    public boolean Remove(int id) {
        return false;
    }

    @Override
    public boolean RemoveByString(String stringa) {
        return false;
    }

    @Override
    public boolean Update(Segreteria object) {
        return false;
    }

    @Override
    public boolean UpdateByString(Segreteria object, String stringa) {
        return false;
    }
}
