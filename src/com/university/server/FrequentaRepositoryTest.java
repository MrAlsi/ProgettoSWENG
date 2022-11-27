package com.university.server;

import com.google.common.collect.Lists;
import com.university.client.model.Corso;
import com.university.client.model.Frequenta;

import java.util.ArrayList;

import java.util.List;

public class FrequentaRepositoryTest implements RepositoryIntString<Frequenta> {
    List<Frequenta> frequentatest= Lists.newArrayList(
            new Frequenta(1,"italiano" )
    );
    @Override
    public Frequenta[] getAll() {
        return frequentatest.toArray(new Frequenta[0]);
    }

    @Override
    public Frequenta[] getArrayByInt(int id) {
        ArrayList<Frequenta> mieiCorsi = new ArrayList<>();
        for(Frequenta corso : frequentatest) {
            if (corso.matricola == id) {
                mieiCorsi.add(corso);
            }
        }
        return mieiCorsi.toArray(new Frequenta[0]);
    }

    @Override
    public Frequenta[] getArrayByString(String id) {
        ArrayList<Frequenta> studenti = new ArrayList<>();
        for(Frequenta studente: frequentatest) {
            if (studente.getNomeCorso().equals(id)) {
                studenti.add(studente);
            }
        }
        return studenti.toArray(new Frequenta[0]);
    }

    @Override
    public boolean Remove(int id, String nome) {
        for(Frequenta f: frequentatest){
            if(f.getMatricola() == id && f.getNomeCorso().equals(nome)){
                frequentatest.remove(f);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean Create(Frequenta object) {
        try{
            frequentatest.add(object);
            return true;
        } catch(Exception e){
            System.out.println("Create Frequenta Repository test " + e);
        }
        return false;
    }
}
