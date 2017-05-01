package filling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

public class PostgreSQLFiller {

    private static final int MOVIES_COUNT = 250000;
    private static final int PEOPLE_COUNT = 500000;
    private static final int REVIEWS_COUNT = 500000;

    public static void fill() {

        Random random = new Random();

        Connection connection;
        Statement statement;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://aws-us-east-1-portal.5.dblayer.com:18951/compose",
                            "admin", "qwer1234");
            statement = connection.createStatement();

            statement.execute("DROP SCHEMA IF EXISTS moviefinder CASCADE");
            statement.execute("CREATE SCHEMA moviefinder");
            statement.execute("CREATE TABLE moviefinder.people(id INT PRIMARY KEY, name TEXT, " +
                    "birthday DATE, country TEXT, occupations TEXT[])");
            statement.execute("CREATE TABLE moviefinder.films(id INT PRIMARY KEY, name TEXT, year INT, " +
                    "duration INT, description TEXT, rating FLOAT, country TEXT, " +
                    "genres TEXT[])");
            statement.execute("CREATE TABLE moviefinder.reviews(id INT PRIMARY KEY, time DATE, " +
                    "mark INT, review TEXT)");
            statement.execute("CREATE TABLE moviefinder.films_people(id SERIAL, film INT, person INT, kind TEXT)");
            statement.execute("CREATE TABLE moviefinder.films_reviews(id SERIAL, film INT, review INT)");
            statement.execute("CREATE TABLE moviefinder.counters(name TEXT, next_id INT)");

            // filling people
            for (int i = 0; i < PEOPLE_COUNT; i++) {
                statement.execute("INSERT INTO moviefinder.people VALUES (" +
                        i + ",\'" +
                        RandomGenerator.generateName() + "\',\'" +
                        RandomGenerator.generateBirthday() + "\',\'" +
                        RandomGenerator.generateCountry() + "\'," +
                        (i < PEOPLE_COUNT / 2 ? "\'{\"actor\"}\'" : "\'{\"producer\"}\'") +
                        "); ");
            }

            // filling films
            for (int i = 0; i < MOVIES_COUNT; i++) {
                List<String> genres = RandomGenerator.generateGenres();
                statement.execute("INSERT INTO moviefinder.films VALUES (" +
                        i + ",\'" +
                        RandomGenerator.generateName() + "\'," +
                        RandomGenerator.generateYear() + "," +
                        RandomGenerator.generateDuration() + ",\'" +
                        RandomGenerator.generateText(150) + "\'," +
                        RandomGenerator.generateRating().floatValue() + ",\'" +
                        RandomGenerator.generateCountry() + "\'," +
                        "\'{\"" + genres.get(0) + "\",\"" + genres.get(1) + "\",\"" + genres.get(2) + "\"}\'" +
                        "); ");
            }

            // filling reviews
            for (int i = 0; i < REVIEWS_COUNT; i++) {
                statement.execute("INSERT INTO moviefinder.reviews VALUES (" +
                        i + ",\'" +
                        RandomGenerator.generateDate() + "\'," +
                        (random.nextInt(10) + 1) + ",\'" +
                        RandomGenerator.generateText(300) + "\'" +
                        "); ");
            }

            // filling connections
            for (int i = 0; i < MOVIES_COUNT; i++) {
                statement.execute("INSERT INTO moviefinder.films_people(film, person, kind) VALUES (" +
                        i + "," +
                        (random.nextInt(PEOPLE_COUNT / 2) + PEOPLE_COUNT / 2) +
                        ",\'producer\'" +
                        "); ");
                for (int j = 0; j < 3; j++) {
                    statement.execute("INSERT INTO moviefinder.films_people(film, person, kind) VALUES (" +
                            i + "," +
                            (random.nextInt(PEOPLE_COUNT / 2)) +
                            ",\'actor\'" +
                            "); ");
                }
            }

            for (int i = 0; i < REVIEWS_COUNT; i++) {
                statement.execute("INSERT INTO moviefinder.films_reviews(film, review) VALUES (" +
                        i + "," +
                        random.nextInt(MOVIES_COUNT) +
                        "); ");
            }

            // filling id-counters
            statement.execute("INSERT INTO moviefinder.counters(name, next_id) VALUES (" +
                    "\'films\'," +
                    MOVIES_COUNT +
                    "); ");
            statement.execute("INSERT INTO moviefinder.counters(name, next_id) VALUES (" +
                    "\'people\'," +
                    PEOPLE_COUNT +
                    "); ");
            statement.execute("INSERT INTO moviefinder.counters(name, next_id) VALUES (" +
                    "\'reviews\'," +
                    REVIEWS_COUNT +
                    "); ");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

}
