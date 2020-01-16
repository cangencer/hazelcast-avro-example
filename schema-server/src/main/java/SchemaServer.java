import com.hazelcast.avro.impl.GenericRecordStreamSerializer;
import com.hazelcast.config.Config;
import com.hazelcast.config.GlobalSerializerConfig;
import com.hazelcast.core.Hazelcast;

public class SchemaServer {

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config
                .getSerializationConfig()
                .setGlobalSerializerConfig(
                        new GlobalSerializerConfig().setClassName(GenericRecordStreamSerializer.class.getName())
                );


        Hazelcast.newHazelcastInstance(config);
    }
}