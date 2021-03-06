package filling;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MongoFiller {

    private static final int MOVIES_COUNT = 250000;
    private static final int PEOPLE_COUNT = 500000;
    private static final int REVIEWS_COUNT = 500000;

    public static void fill() {

        // connect to database
        MongoClientURI uri = new MongoClientURI("mongodb://u4s5:qwer1234" +
                "@moviecluster-shard-00-00-zfyol.mongodb.net:27017," +
                "moviecluster-shard-00-01-zfyol.mongodb.net:27017," +
                "moviecluster-shard-00-02-zfyol.mongodb.net:27017/movieFinder" +
                "?ssl=true&replicaSet=MovieCluster-shard-0&authSource=admin");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase db = mongoClient.getDatabase("movieFinder");
        db.drop();

        Random random = new Random();

        new Thread(() -> {
            // fill collection of people
            MongoCollection collection = db.getCollection("people");
            Document document;
            for (int i = 0; i < PEOPLE_COUNT; i++) {
                document = new Document("_id", i)
                        .append("name", RandomGenerator.generateName())
                        .append("birthday", RandomGenerator.generateBirthday())
                        .append("country", RandomGenerator.generateCountry());

                List<String> occupations = new ArrayList<>();
                if (i < PEOPLE_COUNT / 2)
                    occupations.add("actor");
                else
                    occupations.add("producer");

                document.append("occupations", occupations);

                collection.insertOne(document);
            }
        }).start();

        new Thread(() -> {
            // fill collection of films
            MongoCollection collection = db.getCollection("films");
            Document document;
            for (int i = 0; i < MOVIES_COUNT; i++) {
                document = new Document("_id", i)
                        .append("name", RandomGenerator.generateName())
                        .append("year", RandomGenerator.generateYear())
                        .append("duration", RandomGenerator.generateDuration())
                        .append("countries", new ArrayList<String>() {{
                            add(RandomGenerator.generateCountry());
                        }})
                        .append("rating", RandomGenerator.generateRating())
                        .append("genres", RandomGenerator.generateGenres())
                        .append("description", RandomGenerator.generateText(150))
                        .append("producer_ids", new ArrayList<Integer>() {{
                            add(random.nextInt(PEOPLE_COUNT / 2) + PEOPLE_COUNT / 2);
                        }})
                        .append("actor_ids", new ArrayList<Integer>() {{
                            for (int i = 0; i < 5; i++)
                                add(random.nextInt(PEOPLE_COUNT / 2));
                        }});

                collection.insertOne(document);
            }
        }).start();

        new Thread(() -> {
            // fill collection of reviews
            MongoCollection collection = db.getCollection("reviews");
            Document document;
            for (int i = 0; i < REVIEWS_COUNT; i++) {
                document = new Document("_id", i)
                        .append("author", RandomGenerator.generateName())
                        .append("mark", (int) (Math.round(RandomGenerator.generateRating())))
                        .append("date", RandomGenerator.generateDate())
                        .append("text", RandomGenerator.generateText(300))
                        .append("film_id", random.nextInt(MOVIES_COUNT));

                collection.insertOne(document);
            }
        }).start();

        new Thread(() -> {
            // fill collection of id-counters
            MongoCollection collection = db.getCollection("counters");
            Document document = new Document("_id", "personId").append("next", PEOPLE_COUNT);
            collection.insertOne(document);
            document = new Document("_id", "filmId").append("next", MOVIES_COUNT);
            collection.insertOne(document);
            document = new Document("_id", "reviewId").append("next", REVIEWS_COUNT);
            collection.insertOne(document);
        }).start();
    }

}
