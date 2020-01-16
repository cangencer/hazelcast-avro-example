import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.examples.avro.User;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Benchmark {

    public static String NAME_PREFIX = "name-";
    public static final int ITEM_COUNT = 2_000_000;
    public static final int ITERATION_COUNT = 5;
    private HazelcastInstance instance;
    private IMap<Object, Object> users;

    @Before
    public void setup() {
        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        instance = Hazelcast.newHazelcastInstance(config);
        users = instance.getMap("users");

        NAME_PREFIX = "name-" + IntStream.range(0, 10).mapToObj(Integer::toString).collect(Collectors.joining());
        System.out.println("Using prefix length" + NAME_PREFIX.length());

    }
    @Test
    public void test() {
        System.out.println("PORTABLE");
        bench(i -> new PortableUser(i, NAME_PREFIX + i, NAME_PREFIX + i));

        System.out.println("AVRO");
        bench(i -> new User(i, NAME_PREFIX + i, NAME_PREFIX + i));

        System.out.println("SERIALIZABLE");
        bench(i -> new SerializableUser(i, NAME_PREFIX + i, NAME_PREFIX + i));

        System.out.println("IDENTIFIED_USER");
        bench(i -> new IdentifiedUser(i, NAME_PREFIX + i, NAME_PREFIX + i));
    }

    private void bench(IntFunction f) {
        Map<Object, Object> tmpMap = new HashMap<>(1024);
        for (int i = 0; i < ITERATION_COUNT; i++) {
            users.clear();
            long start = System.nanoTime();
            for (int j = 0; j < ITEM_COUNT; j++) {
                tmpMap.put(j, f.apply(j));
                if (tmpMap.size() == 1024) {
                    users.putAll(tmpMap);
                    tmpMap.clear();
                }
            }
            users.putAll(tmpMap);
            long elapsed = System.nanoTime() - start;
            System.out.println("Took: " + TimeUnit.NANOSECONDS.toMillis(elapsed) + "ms");
            System.out.println("Size: " + users.getLocalMapStats().getHeapCost() / 1024.0 / 1024.0 + " MB");
        }
    }
}
