package com.university.client.model.Serializer;

import com.university.client.model.Admin;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerAdmin implements Serializer<Admin>, Serializable{
    @Override
    public void serialize(DataOutput2 dataOutput2, Admin admin) throws IOException {
        dataOutput2.writeUTF(admin.getNome());
        dataOutput2.writeUTF(admin.getCognome());
        dataOutput2.writeUTF(admin.getMail());
        dataOutput2.writeUTF(admin.getPassword());
    }

    @Override
    public Admin deserialize(DataInput2 dataInput2, int i) throws IOException {
        return new Admin(dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readUTF(), dataInput2.readUTF());
    }
}
