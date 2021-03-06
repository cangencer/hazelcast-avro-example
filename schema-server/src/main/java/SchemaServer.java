import com.hazelcast.avro.AvroConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;

public class SchemaServer {

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);

        AvroConfig.configure(config);

        Hazelcast.newHazelcastInstance(config);
    }
}