package com.university.server;

public interface RepositoryInt<K>{
    public K GetById(int id);
    public K[] getAll();
    public boolean Create(K object);
    public boolean Remove(int id);
    public boolean Update(K object);

}
