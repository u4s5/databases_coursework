package filling;

import com.mongodb.MongoClient;
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
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("movieDatabase");
        db.drop();

        Random random = new Random();
        Document document;

        // fill collection of people
        MongoCollection collection = db.getCollection("people");
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

        // fill collection of films
        collection = db.getCollection("films");
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

        // fill collection of reviews
        collection = db.getCollection("reviews");
        for (int i = 0; i < REVIEWS_COUNT; i++) {
            document = new Document("_id", i)
                    .append("author", RandomGenerator.generateName())
                    .append("mark", (int) (Math.round(RandomGenerator.generateRating())))
                    .append("date", RandomGenerator.generateDate())
                    .append("text", RandomGenerator.generateText(300))
                    .append("film_id", random.nextInt(MOVIES_COUNT));

            collection.insertOne(document);
        }

        mongoClient.close();
    }

}
