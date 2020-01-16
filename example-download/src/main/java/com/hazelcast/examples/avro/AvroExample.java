package com.hazelcast.examples.avro;

import com.hazelcast.avro.AvroStreamSerializer;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GlobalSerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicates;

import java.util.Collection;

public class AvroExample {

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        config.getSerializationConfig().setGlobalSerializerConfig(
                new GlobalSerializerConfig()
                        .setOverrideJavaSerialization(true)
                        .setClassName(AvroStreamSerializer.class.getName())
        );
        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);

        IMap<Integer, User> users = client.getMap("users");

        for (int i = 0; i < 1000; i++) {
            users.put(i, new User(i, "name-" + i, "lastname-" + i));
        }

        users.entrySet().forEach(System.out::println);

        System.out.println("Running query");

        Collection<User> results = users.values(Predicates.equal("name", "name-10"));

        System.out.println(results);
        client.shutdown();
    }
}
