package com.university.client.model.Serializer;

import com.university.client.model.Studente;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerStudente implements Serializer<Studente>, Serializable {
    @Override
    public void serialize(DataOutput2 dataOutput2, Studente studente) throws IOException {
        dataOutput2.writeUTF(studente.getNome());
        dataOutput2.writeUTF(studente.getCognome());
        dataOutput2.writeUTF(studente.getMail());
        dataOutput2.writeUTF(studente.getPassword());
        dataOutput2.writeUTF(studente.getDataNascita());
        dataOutput2.writeInt(studente.getMatricola());
    }

    @Override
    public Studente deserialize(DataInput2 dataInput2, int i) throws IOException {
        return new Studente(dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readInt());
    }
}
