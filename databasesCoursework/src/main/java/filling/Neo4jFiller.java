package filling;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import java.util.List;
import java.util.Random;

public class Neo4jFiller {

    private static final int MOVIES_COUNT = 25000;
    private static final int PEOPLE_COUNT = 50000;
    private static final int REVIEWS_COUNT = 50000;

    public static void fill() {

        Random random = new Random();

        // connecting to database
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "qwer1234"));
        Session session = driver.session();

        session.run("MATCH (n) DETACH DELETE n;");

        // filling people
        for (int i = 0; i < PEOPLE_COUNT; i++) {
            session.run("CREATE (p:Person {" +
                    "id: " + i + ", " +
                    "name: \"" + RandomGenerator.generateName() + "\", " +
                    "birthday: \"" + RandomGenerator.generateBirthday() + "\", " +
                    "country: \"" + RandomGenerator.generateCountry() + "\"});");
        }

        // filling movies
        for (int i = 0; i < MOVIES_COUNT; i++) {
            List<String> genres = RandomGenerator.generateGenres();
            session.run("CREATE (f:Film {" +
                    "id: " + i + ", " +
                    "name: \"" + RandomGenerator.generateName() + "\", " +
                    "year: " + RandomGenerator.generateYear() + ", " +
                    "duration: " + RandomGenerator.generateDuration() + ", " +
                    "description: \"" + RandomGenerator.generateText(100) + "\", " +
                    "rating: " + RandomGenerator.generateRating() + ", " +
                    "genres: [\"" + genres.get(0) + "\", \"" + genres.get(1) + "\", \"" + genres.get(2) + "\"], " +
                    "country: \"" + RandomGenerator.generateCountry() + "\"});");
        }

        // filling reviews
        for (int i = 0; i < REVIEWS_COUNT; i++) {
            session.run("CREATE (r:Review {" +
                    "id: " + i + ", " +
                    "date: \"" + RandomGenerator.generateDate() + "\", " +
                    "mark: " + (random.nextInt(10) + 1) + ", " +
                    "review: \"" + RandomGenerator.generateText(150) + "\"});");
        }

        // filling relations
        for (int i = 0; i < MOVIES_COUNT; i++) {
            session.run("MATCH (p:Person {id: " + (random.nextInt(PEOPLE_COUNT / 2) + PEOPLE_COUNT / 2) + "}), " +
                    "(f:Film {id: " + i + "}) " +
                    "MERGE (p)-[:DIRECTS]->(f);");

            for (int j = 0; j < 3; j++) {
                session.run("MATCH (p:Person {id: " + (random.nextInt(PEOPLE_COUNT / 2)) + "}), " +
                        "(f:Film {id: " + i + "}) " +
                        "MERGE (p)-[:ACTS_IN]->(f);");
            }
        }

        for (int i = 0; i < REVIEWS_COUNT; i++) {
            session.run("MATCH (r:Review {id: " + i + "}), " +
                    "(f:Film {id: " + random.nextInt(MOVIES_COUNT) + "}) " +
                    "MERGE (p)-[:WRITTEN_FOR]->(f);");
        }

        session.close();
        driver.close();

    }

}
