package com.hazelcast.examples.avro;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.avro.AvroClientConfig;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicates;

import java.util.Collection;

public class AvroExample {

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        AvroClientConfig.configure(config);

        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);

        client.getSchemaRegistry().add(User.getClassSchema());

        IMap<Integer, User> users = client.getMap("users");

        System.out.println("Add index");
        users.addIndex("name", false);

        for (int i = 0; i < 1_000; i++) {
            users.put(i, new User(i, "name-" + i, "lastname-" + i));
        }

        System.out.println("Take some 10 entries");

        users.entrySet().stream().limit(10).forEach(System.out::println);

        System.out.println("Running query");

        Collection<User> results = users.values(Predicates.equal("name", "name-10"));

        System.out.println(results);

        client.shutdown();
    }
}
