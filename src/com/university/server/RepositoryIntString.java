package com.university.server;

public interface RepositoryIntString<K> {
    public K[] getAll();

    public K[] getArrayByInt(int id);

    public K[] getArrayByString(String id);

    public boolean Remove(int id,String nome);

    public boolean Create(K object);


}
