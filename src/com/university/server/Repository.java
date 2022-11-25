package com.university.server;

public interface Repository <K>{
    public K GetById(int id);

    public K GetByString (String stringa );

    public K[] getAll();

    public boolean Create(K object);

    public boolean Remove(int id);

    public boolean RemoveByString(String stringa);

    public boolean Update(K object);
    public boolean UpdateByString(K object, String stringa);

}
