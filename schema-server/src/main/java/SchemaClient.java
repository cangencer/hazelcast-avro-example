import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import org.apache.avro.Schema;

public class SchemaClient {

    public static void main(String[] args) throws Exception {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();

        System.out.println("The following schemas were found: ");
        for (Schema schema : client.getSchemaRegistry().list()) {
            System.out.println(schema.getName() + ": " + schema);
        }

        client.shutdown();
    }
}