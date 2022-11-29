package com.university.server;

public interface RepositoryDoubleInt<K> {
    public K[] getAll();
    public K[] getArrayById1(int id);
    public K[] getArrayById2(int id);
    public boolean Create(K object);
    public boolean Remove(int id1, int id2);
    public boolean Update(K object, int id1, int id2);

}
