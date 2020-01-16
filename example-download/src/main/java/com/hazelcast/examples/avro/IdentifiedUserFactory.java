package com.hazelcast.examples.avro;

import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

public class IdentifiedUserFactory implements DataSerializableFactory {
    public static final int ID = 1;

    @Override
    public IdentifiedDataSerializable create(int typeId) {
        switch (typeId) {
            case 0:
                return new IdentifiedUser();
            default:
                throw new RuntimeException("invalid");
        }
    }
}
