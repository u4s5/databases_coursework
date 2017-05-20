package filling;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.Session;

import java.util.List;
import java.util.Random;

public class CassandraFiller {

    private static final int MOVIES_COUNT = 10000;
    private static final int PEOPLE_COUNT = 20000;
    private static final int REVIEWS_COUNT = 20000;

    public static void fill() {

        Random random = new Random();

        // connecting to database
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.newSession();

        session.execute("CREATE KEYSPACE IF NOT EXISTS movieFinder WITH replication = {"
                + " 'class': 'SimpleStrategy', "
                + " 'replication_factor': '3' "
                + "};");

        // creating tables
        session.execute("DROP TABLE IF EXISTS movieFinder.people; ");
        session.execute("CREATE TABLE movieFinder.people(id int PRIMARY KEY, name text, " +
                "birthday date, country text, occupations set<text>); ");
        session.execute("DROP TABLE IF EXISTS movieFinder.films; ");
        session.execute("CREATE TABLE movieFinder.films(id int PRIMARY KEY, name text, year int, " +
                "duration int, description text, rating float, country text, " +
                "genres set<text>); ");
        session.execute("DROP TABLE IF EXISTS movieFinder.reviews; ");
        session.execute("CREATE TABLE movieFinder.reviews(id int PRIMARY KEY, time date, " +
                "mark int, review text); ");
        session.execute("DROP TABLE IF EXISTS movieFinder.films_people; ");
        session.execute("CREATE TABLE movieFinder.films_people(film int, person int, kind text, " +
                "PRIMARY KEY(film, person, kind)); ");
        session.execute("DROP TABLE IF EXISTS movieFinder.films_reviews");
        session.execute("CREATE TABLE movieFinder.films_reviews(film int, review int, " +
                "PRIMARY KEY(film, review)); ");

        // filling people
        for (int i = 0; i < PEOPLE_COUNT; i++) {
            session.execute("INSERT INTO movieFinder.people(\"id\", \"name\", \"birthday\", " +
                    "\"country\", \"occupations\") VALUES (" +
                    i + ",\'" +
                    RandomGenerator.generateName() + "\',\'" +
                    LocalDate.fromMillisSinceEpoch(RandomGenerator.generateBirthday().getTime()) + "\',\'" +
                    RandomGenerator.generateCountry() + "\'," +
                    (i < PEOPLE_COUNT / 2 ? "{\'actor\'}" : "{\'producer\'}") +
                    "); ");
        }

        // filling films
        for (int i = 0; i < MOVIES_COUNT; i++) {
            List<String> genres = RandomGenerator.generateGenres();
            session.execute("INSERT INTO movieFinder.films(\"id\", \"name\", \"year\", \"duration\"," +
                    "\"description\", \"rating\", \"country\", \"genres\") VALUES (" +
                    i + ",\'" +
                    RandomGenerator.generateName() + "\'," +
                    RandomGenerator.generateYear() + "," +
                    RandomGenerator.generateDuration() + ",\'" +
                    RandomGenerator.generateText(150) + "\'," +
                    RandomGenerator.generateRating().floatValue() + ",\'" +
                    RandomGenerator.generateCountry() + "\'," +
                    "{\'" + genres.get(0) + "\',\'" + genres.get(1) + "\',\'" + genres.get(2) + "\'}" +
                    "); ");
        }

        // filling reviews
        for (int i = 0; i < REVIEWS_COUNT; i++) {
            session.execute("INSERT INTO movieFinder.reviews(\"id\", \"time\", \"mark\", \"review\")" +
                    "VALUES (" +
                    i + ",\'" +
                    LocalDate.fromMillisSinceEpoch(RandomGenerator.generateDate().getTime()) + "\'," +
                    (random.nextInt(10) + 1) + ",\'" +
                    RandomGenerator.generateText(300) + "\'" +
                    "); ");
        }

        // filling connections
        for (int i = 0; i < MOVIES_COUNT; i++) {
            session.execute("INSERT INTO movieFinder.films_people(\"film\", \"person\", \"kind\")" +
                    "VALUES (" +
                    i + "," +
                    (random.nextInt(PEOPLE_COUNT / 2) + PEOPLE_COUNT / 2) +
                    ",\'producer\'" +
                    "); ");
            for (int j = 0; j < 3; j++) {
                session.execute("INSERT INTO movieFinder.films_people(\"film\", \"person\", \"kind\")" +
                        "VALUES (" +
                        i + "," +
                        (random.nextInt(PEOPLE_COUNT / 2)) +
                        ",\'actor\'" +
                        "); ");
            }
        }

        for (int i = 0; i < REVIEWS_COUNT; i++) {
            session.execute("INSERT INTO movieFinder.films_reviews(\"review\", \"film\") VALUES (" +
                    i + "," +
                    random.nextInt(MOVIES_COUNT) +
                    "); ");
        }

        session.close();
        cluster.close();
    }

}
