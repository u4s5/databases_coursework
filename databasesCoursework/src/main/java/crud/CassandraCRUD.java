package crud;

import com.datastax.driver.core.*;

public class CassandraCRUD {

    private static Session session;

    static {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.newSession();
    }

    public static String createFilm(String name, String year, String duration,
                                    String country, String rating,
                                    String genre, String description,
                                    String producerId, String actor1Id,
                                    String actor2Id, String actor3Id) {
        String id;

        try {
            ResultSet resultSet = session.execute("SELECT COUNT(*) AS count FROM movieFinder.films;");
            id = resultSet.one().toString();
            id = id.substring(4, id.length() - 1);

            session.execute("INSERT INTO movieFinder.films(\"id\", \"name\", " +
                    "\"year\", \"duration\"," +
                    "\"description\", \"rating\", \"country\", \"genres\") VALUES (" +
                    id + ",\'" +
                    name + "\'," +
                    year + "," +
                    duration + ",\'" +
                    description + "\'," +
                    Float.parseFloat(rating) + ",\'" +
                    country + "\'," +
                    "{\'" + genre + "\'}" +
                    "); ");

            session.execute("INSERT INTO movieFinder.films_people(\"film\", \"person\", \"kind\")" +
                    "VALUES (" +
                    id + "," +
                    producerId +
                    ",\'producer\'" +
                    "); ");
            session.execute("INSERT INTO movieFinder.films_people(\"film\", \"person\", \"kind\")" +
                    "VALUES (" +
                    id + "," +
                    actor1Id +
                    ",\'actor\'" +
                    "); ");
            session.execute("INSERT INTO movieFinder.films_people(\"film\", \"person\", \"kind\")" +
                    "VALUES (" +
                    id + "," +
                    actor2Id +
                    ",\'actor\'" +
                    "); ");
            session.execute("INSERT INTO movieFinder.films_people(\"film\", \"person\", \"kind\")" +
                    "VALUES (" +
                    id + "," +
                    actor3Id +
                    ",\'actor\'" +
                    "); ");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return findFilm(name);
    }

    public static String createPerson(String name, String birthday,
                                      String country, String occupation) {
        String id;

        try {
            ResultSet resultSet = session.execute("SELECT COUNT(*) AS count FROM movieFinder.people;");
            id = resultSet.one().toString();
            id = id.substring(4, id.length() - 1);

            session.execute("INSERT INTO movieFinder.people(\"id\", \"name\", \"birthday\", " +
                    "\"country\", \"occupations\") VALUES (" +
                    id + ",\'" +
                    name + "\',\'" +
                    LocalDate.fromMillisSinceEpoch(StringToDateParser.parse(birthday).getTime()) + "\',\'" +
                    country + "\'," +
                    "{\'" + occupation + "\'}" +
                    "); ");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return findPerson(name);
    }

    public static String createReview(String mark, String date,
                                      String text, String filmId) {

        String id;

        try {
            ResultSet resultSet = session.execute("SELECT COUNT(*) AS count FROM movieFinder.reviews;");
            id = resultSet.one().toString();
            id = id.substring(4, id.length() - 1);

            session.execute("INSERT INTO movieFinder.reviews(\"id\", \"time\", \"mark\", \"review\")" +
                    "VALUES (" +
                    id + ",\'" +
                    LocalDate.fromMillisSinceEpoch(StringToDateParser.parse(date).getTime()) + "\'," +
                    mark + ",\'" +
                    text + "\'" +
                    "); ");

            session.execute("INSERT INTO movieFinder.films_reviews(\"review\", \"film\") VALUES (" +
                    id + "," +
                    filmId +
                    "); ");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return findReview(id);
    }

    public static String findFilm(String name) {

        String result;

        try {
            Row row = session.execute("SELECT * FROM movieFinder.films " +
                    "WHERE name= \'" + name + "\' ALLOW FILTERING;").one();
            result = row.getString("name") + "\n" + row.getInt("id");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return result;
    }

    public static String findPerson(String name) {
        String result;

        try {
            Row row = session.execute("SELECT * AS count FROM movieFinder.people " +
                    "WHERE name= " + name + ";").one();
            result = row.getString("name") + "\n" + row.getInt("id");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return result;
    }

    public static String findReview(String id) {
        String result;

        try {
            Row row = session.execute("SELECT * AS count FROM movieFinder.reviews " +
                    "WHERE id = " + id + ";").one();
            result = row.getString("review") + "\n" + row.getInt("id");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return result;
    }

    public static String editFilm(String id, String newName, String newYear,
                                  String newDuration, String newCountry,
                                  String newRating, String newGenre,
                                  String newDescription, String newProducerId,
                                  String newActor1Id, String newActor2Id,
                                  String newActor3Id) {
        try {
            session.execute("UPDATE movieFinder.films " +
                    "SET name = \'" + newName + "\', " +
                    "year = " + newYear + ", " +
                    "duration = " + newDuration + ", " +
                    "country = \'" + newCountry + "\', " +
                    "rating = " + newRating + ", " +
                    "description = \'" + newDescription + "\', " +
                    "genres = {\'" + newGenre + "\'} " +
                    "WHERE id = " + id + "; ");
/*
            session.execute("UPDATE movieFinder.films_people " +
                    "SET person = " + newProducerId + ", " +
                    "kind = " + "\'producer\' " +
                    "WHERE film = " + id + "; ");
            session.execute("UPDATE movieFinder.films_people " +
                    "SET person = " + newActor1Id + ", " +
                    "kind = " + "\'actor\' " +
                    "WHERE film = " + id + "; ");
            session.execute("UPDATE movieFinder.films_people " +
                    "SET person = " + newActor2Id + ", " +
                    "kind = " + "\'actor\' " +
                    "WHERE film = " + id + "; ");
            session.execute("UPDATE movieFinder.films_people " +
                    "SET person = " + newActor3Id + ", " +
                    "kind = " + "\'actor\' " +
                    "WHERE film = " + id + "; ");
                    */
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return findFilm(newName);
    }

    public static String editPerson(String id, String newName, String newBirthday,
                                    String newCountry, String newOccupation) {

        try {
            session.execute("UPDATE movieFinder.people " +
                    "SET name = \'" + newName + "\', " +
                    "birthday = " + LocalDate.fromMillisSinceEpoch(StringToDateParser.parse(newBirthday).getTime()) + ", " +
                    "country = \'" + newCountry + "\', " +
                    "occupations = {\'" + newOccupation + "\'} " +
                    "WHERE id = " + id + "; ");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return findPerson(newName);
    }

    public static String editReview(String id, String newMark,
                                    String newDate, String newText, String newFilmId) {

        try {
            session.execute("UPDATE movieFinder.reviews " +
                    "SET time = " + LocalDate.fromMillisSinceEpoch(StringToDateParser.parse(newDate).getTime()) + ", " +
                    "mark = " + newMark + ", " +
                    "review = \'" + newText + "\' " +
                    "WHERE id = " + id + "; ");

            session.execute("UPDATE movieFinder.films_reviews " +
                    "SET film = " + newFilmId + " " +
                    "WHERE review = " + id + "; ");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return findReview(id);
    }

    public static String deleteFilm(String id) {
        try {
            session.execute("DELETE FROM movieFinder.films " +
                    "WHERE id= " + id + ";");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return id;
    }

    public static String deletePerson(String id) {
        try {
            session.execute("DELETE FROM movieFinder.people " +
                    "WHERE id= " + id + ";");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return id;
    }

    public static String deleteReview(String id) {
        try {
            session.execute("DELETE FROM movieFinder.reviews " +
                    "WHERE id= " + id + ";");
        } catch (Exception e) {
            System.err.println("Cassandra exception");
            return null;
        }

        return id;
    }
}
