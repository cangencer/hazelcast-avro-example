import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;

public class SchemaClient {

    public static void main(String[] args) throws Exception {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();

        System.out.println("Listing all schemas");
        client.getReplicatedMap("hz:schemas").forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });

        client.shutdown();
    }
}