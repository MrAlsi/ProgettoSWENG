package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Segreteria;

import java.util.Arrays;
import java.util.List;

public class SegreteriaRepositoryTest implements RepositoryString<Segreteria> {

    List<Segreteria> segreteriaTest= Lists.newArrayList(
            new Segreteria("Claudia", "Codeluppi", "claudia.codeluppi@segreteria.university.com", "mamma")
    );
    @Override
    public Segreteria GetById(String stringa) {
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
        try{
            segreteriaTest.add(object);
            return true;
        }catch(Exception e){
            System.out.println("segreteriaTest " + e);
        }
        return false;
    }

    @Override
    public boolean Remove(String stringa) {
        try{
            segreteriaTest.remove(stringa);
            return true;
        }catch(Exception e){
            System.out.println("segreteriaTest " + e);
        }
        return false;
    }

    @Override
    public boolean Update(Segreteria object, String stringa) {
        for(Segreteria s : segreteriaTest){
            if(s.getMail().equals(object.getMail())){
                segreteriaTest.set(segreteriaTest.indexOf(s), object);
                segreteriaTest.remove(s);
                return true;
            }
        }
        return false;
    }
}
