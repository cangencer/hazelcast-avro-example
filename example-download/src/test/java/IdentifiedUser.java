import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

import java.io.IOException;

public class IdentifiedUser implements IdentifiedDataSerializable {

    public int id;
    public String name;
    public String lastName;

    public IdentifiedUser(int id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public IdentifiedUser() {

    }


    @Override
    public int getFactoryId() {
        return IdentifiedUserFactory.ID;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(name);
        out.writeUTF(lastName);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        id = in.readInt();
        name = in.readUTF();
        lastName = in.readUTF();
    }
}