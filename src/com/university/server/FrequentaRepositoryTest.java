package com.university.server;

import com.university.client.model.Frequenta;

public class FrequentaRepositoryTest implements RepositoryIntString<Frequenta> {
    @Override
    public Frequenta[] getAll() {
        return new Frequenta[0];
    }

    @Override
    public Frequenta[] getArrayByInt(int id) {
        return new Frequenta[0];
    }

    @Override
    public Frequenta[] getArrayByString(String id) {
        return new Frequenta[0];
    }

    @Override
    public boolean Remove(int id, String nome) {
        return false;
    }

    @Override
    public boolean Create(Frequenta object) {
        return false;
    }
}
