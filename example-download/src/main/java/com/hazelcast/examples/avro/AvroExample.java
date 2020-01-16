package com.hazelcast.examples.avro;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class AvroExample {

    public static void main(String[] args) {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();

        IMap<Integer, User> users = client.getMap("users");

        for (int i = 0; i < 1000; i++) {
            users.put(i, new User(i, "name-" + i, "lastname-" + i));
        }

        users.entrySet().forEach(System.out::println);
        client.shutdown();
    }
}
