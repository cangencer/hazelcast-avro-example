package com.hazelcast.examples.avro;

import java.io.Serializable;

public class SerializableUser implements Serializable {

    public int id;
    public String name;
    public String lastName;

    public SerializableUser(int id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }


}
