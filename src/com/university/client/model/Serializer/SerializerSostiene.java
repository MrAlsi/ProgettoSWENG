package com.university.client.model.Serializer;

import com.university.client.model.Sostiene;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerSostiene implements Serializer<Sostiene>, Serializable {
    @Override
    public void serialize(DataOutput2 dataOutput2, Sostiene sostiene) throws IOException {
        dataOutput2.writeInt(sostiene.getMatricola());
        dataOutput2.writeInt(sostiene.getCodEsame());
        dataOutput2.writeUTF(sostiene.getNomeCorso());
        dataOutput2.writeUTF(sostiene.getData());
        dataOutput2.writeUTF(sostiene.getOra());
        dataOutput2.writeInt(sostiene.getVoto());
        dataOutput2.writeBoolean(sostiene.getAccettato());
    }

    @Override
    public Sostiene deserialize(DataInput2 dataInput2, int i) throws IOException {
        return new Sostiene(dataInput2.readInt(), dataInput2.readInt(), dataInput2.readUTF(),dataInput2.readUTF(),dataInput2.readUTF(), dataInput2.readInt(), dataInput2.readBoolean());
    }
}
