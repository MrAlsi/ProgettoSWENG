package com.university.client.model.Serializer;

import com.university.client.model.Admin;
import com.university.client.model.Segreteria;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerSegreteria implements Serializer<Segreteria>, Serializable {
    @Override
    public void serialize(DataOutput2 dataOutput2, Segreteria segreteria) throws IOException {
        dataOutput2.writeUTF(segreteria.getNome());
        dataOutput2.writeUTF(segreteria.getCognome());
        dataOutput2.writeUTF(segreteria.getMail());
        dataOutput2.writeUTF(segreteria.getPassword());
    }

    @Override
    public Segreteria deserialize(DataInput2 dataInput2, int i) throws IOException {
        return new Segreteria(dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readUTF());
    }
}
