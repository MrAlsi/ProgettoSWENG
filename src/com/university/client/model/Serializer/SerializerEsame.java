package com.university.client.model.Serializer;

import com.university.client.model.Esame;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerEsame implements Serializer<Esame>, Serializable {

    private static final long serialVersionUID = 2L;

    @Override
    public void serialize(DataOutput2 dataOutput2, Esame esame) throws IOException {
        dataOutput2.writeInt(esame.getCodEsame());
        dataOutput2.writeUTF(esame.getNomeCorso());
        dataOutput2.writeUTF(esame.getData());
        dataOutput2.writeUTF(esame.getOra());
        dataOutput2.writeUTF(esame.getAula());
        dataOutput2.writeUTF(esame.getAula());
    }

    @Override
    public Esame deserialize(DataInput2 input, int available) throws IOException {
        return new Esame(input.readInt(),input.readUTF(),input.readUTF(),input.readUTF(),input.readUTF(),input.readUTF());
    }
}