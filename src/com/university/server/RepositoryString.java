package com.university.server;

public interface RepositoryString<K> {

    public K GetById(String stringa);

    public K[] getAll();

    public boolean Create(K object);

    public boolean Remove(String stringa);

    public boolean Update(K object, String stringa);

}
