package com.university.client.model.Serializer;

import com.university.client.model.Frequenta;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerFrequenta implements Serializer<Frequenta>, Serializable {
    @Override
    public void serialize(DataOutput2 dataOutput2, Frequenta frequenta) throws IOException {
        dataOutput2.writeInt(frequenta.getMatricola());
        dataOutput2.writeUTF(frequenta.getNomeCorso());
    }

    @Override
    public Frequenta deserialize(DataInput2 dataInput2, int i) throws IOException {
        return new Frequenta(dataInput2.readInt(), dataInput2.readUTF());
    }
}
