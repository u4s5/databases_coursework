import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MongoFill {

    private static final int MOVIES_COUNT = 1000000;
    private static final int PEOPLE_COUNT = 2000000;
    private static final int REVIEWS_COUNT = 2000000;

    public static void main(String[] args) throws IOException {
        // connect to database
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("movieDatabase");

        // fill collection of people
        MongoCollection collection = db.getCollection("people");
        Random random = new Random();
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
                    .append("author_name", RandomGenerator.generateName())
                    .append("mark", Math.round(RandomGenerator.generateRating()))
                    .append("date", RandomGenerator.generateDate())
                    .append("text", RandomGenerator.generateText(300))
                    .append("film_id", random.nextInt(MOVIES_COUNT));

            collection.insertOne(document);
        }
    }

}
