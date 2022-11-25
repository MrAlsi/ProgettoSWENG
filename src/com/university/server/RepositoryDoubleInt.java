package com.university.server;

public interface RepositoryDoubleInt<K> {
    public K GetById(int id);

    public K[] getArrayById(int id);

    public K[] getAll();

    public boolean Create(K object);

    public boolean Remove(int id);

}
