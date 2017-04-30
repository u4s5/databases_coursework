import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

public class TimeTest {

    private static final int TEST_COUNT = 1000;
    private static Random random;

    static {
        random = new Random();
    }

    public static void testMongo() {
        MongoClientURI uri = new MongoClientURI("mongodb://u4s5:qwer1234" +
                "@moviecluster-shard-00-00-zfyol.mongodb.net:27017," +
                "moviecluster-shard-00-01-zfyol.mongodb.net:27017," +
                "moviecluster-shard-00-02-zfyol.mongodb.net:27017/movieFinder" +
                "?ssl=true&replicaSet=MovieCluster-shard-0&authSource=admin");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase db = mongoClient.getDatabase("movieFinder");

        MongoCollection collection = db.getCollection("films");

        long start = System.currentTimeMillis();

        for (int i = 0; i < TEST_COUNT; i++) {
            System.out.println(collection.find(eq("_id", random.nextInt(250000))).first().toString());
        }

        System.out.println("____________________\nMONGO\n");
        System.out.println("Average time of getting 1 record (ms) = " + (System.currentTimeMillis() - start) / TEST_COUNT);
    }

    public static void testRedis() {
        Jedis jedis = new Jedis("localhost");

        long start = System.currentTimeMillis();

        for (int i = 0; i < TEST_COUNT; i++) {
            System.out.println(jedis.hgetAll("film:" + random.nextInt(250000)).toString());
        }

        System.out.println("____________________\nREDIS\n");
        System.out.println("Average time of getting 1 record (ms) = " + (System.currentTimeMillis() - start) / TEST_COUNT);
    }

    public static void testCassandra() {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.newSession();

        long start = System.currentTimeMillis();

        for (int i = 0; i < TEST_COUNT; i++) {
            System.out.println(session.execute("SELECT name FROM movieFinder.films WHERE id=" + random.nextInt(250000)).one().getString("name"));
        }

        System.out.println("____________________\nCASSANDRA\n");
        System.out.println("Average time of getting 1 record (ms) = " + (System.currentTimeMillis() - start) / TEST_COUNT);
    }

    public static void testNeo4j() {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "qwer1234"));
        org.neo4j.driver.v1.Session session = driver.session();

        long start = System.currentTimeMillis();

        for (int i = 0; i < TEST_COUNT; i++) {
            System.out.println(session.run("MATCH (f:Film) WHERE f.id = " + random.nextInt(25000) + " RETURN f.name AS name").next().get("name").asString());
        }

        System.out.println("____________________\nNEO4J\n");
        System.out.println("Average time of getting 1 record (ms) = " + (System.currentTimeMillis() - start) / TEST_COUNT);
    }

    public static void testPostgreSQL() {

        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/moviefinder",
                            "user1", "password");
            statement = connection.createStatement();

            long start = System.currentTimeMillis();

            ResultSet resultSet;
            for (int i = 0; i < TEST_COUNT; i++) {
                resultSet = statement.executeQuery("SELECT name FROM moviefinder.films WHERE id=" + random.nextInt(250000));
                resultSet.next();
                System.out.println(resultSet.getString("name"));
            }

            System.out.println("____________________\nPOSTGRESQL\n");
            System.out.println("Average time of getting 1 record (ms) = " + (System.currentTimeMillis() - start) / TEST_COUNT);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
