import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import redis.clients.jedis.Jedis;

import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

public class TimeTest {

    private static final int TEST_COUNT = 1000;
    private static Random random;

    static {
        random = new Random();
    }

    public static void testMongo() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("movieDatabase");

        MongoCollection collection = db.getCollection("films");

        long start = System.currentTimeMillis();

        for (int i = 0; i < TEST_COUNT; i++) {
            System.out.println(collection.find(eq("_id", random.nextInt(1000000))).first().toString());
        }

        System.out.println("____________________");
        System.out.println("Average time of getting 1 record (ms) = " + (System.currentTimeMillis() - start) / TEST_COUNT);
    }

    public static void testRedis() {
        Jedis jedis = new Jedis("localhost");

        long start = System.currentTimeMillis();

        for (int i = 0; i < TEST_COUNT; i++) {
            System.out.println(jedis.hgetAll("film:" + random.nextInt(250000)).toString());
        }

        System.out.println("____________________");
        System.out.println("Average time of getting 1 record (ms) = " + (System.currentTimeMillis() - start) / TEST_COUNT);
    }

}
