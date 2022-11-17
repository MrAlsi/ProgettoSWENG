package com.university.client.model.Serializer;

import com.university.client.model.Corso;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerCorso implements Serializer<Corso>, Serializable {
    private static final long serialVersionUID = 2L;

    @Override
    public void serialize(DataOutput2 dataOutput2, Corso corso) throws IOException {
        dataOutput2.writeUTF(corso.getNome());
        dataOutput2.writeUTF(corso.getDataInizio());
        dataOutput2.writeUTF(corso.getDataFine());
        dataOutput2.writeUTF(corso.getDescrizione());
        dataOutput2.writeInt(corso.getCoDocente());
        dataOutput2.writeInt(corso.getDocente());
        dataOutput2.writeInt(corso.getEsame());
    }

    @Override
    public Corso deserialize(DataInput2 dataInput2, int i) throws IOException {
        return new Corso(dataInput2.readUTF(),dataInput2.readUTF(),dataInput2.readUTF(),dataInput2.readUTF(), dataInput2.readInt(), dataInput2.readInt(), dataInput2.readInt());
    }
}
