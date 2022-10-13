package com.university.client.model.Serializer;

import com.university.client.model.Docente;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerDocente implements Serializer<Docente>, Serializable {

    @Override
    public void serialize(DataOutput2 dataOutput2, Docente docente) throws IOException {
        dataOutput2.writeUTF(docente.getNome());
        dataOutput2.writeUTF(docente.getCognome());
        dataOutput2.writeUTF(docente.getMail());
        dataOutput2.writeUTF(docente.getPassword());
        dataOutput2.writeInt(docente.getCodDocente());
    }

    @Override
    public Docente deserialize(DataInput2 dataInput2, int i) throws IOException {
        return new Docente(dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readInt());    }
}
