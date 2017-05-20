package crud;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresqlCRUD {

    private static Statement statement;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager
                    .getConnection("jdbc:postgresql://aws-us-east-1-portal.5.dblayer.com:18951/compose",
                            "admin", "qwer1234");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static String createFilm(String name, String year, String duration,
                                    String country, String rating,
                                    String genre, String description,
                                    String producerId, String actor1Id,
                                    String actor2Id, String actor3Id) {
        String id;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM moviefinder.counters WHERE name=\'films\'; ");
            resultSet.next();
            id = resultSet.getString("next_id");
            statement.execute("UPDATE moviefinder.counters SET next_id=" +
                    (Integer.parseInt(id) + 1) +
                    " WHERE name=\'films\'; ");

            statement.execute("INSERT INTO moviefinder.films VALUES (" +
                    id + ",\'" +
                    name + "\'," +
                    year + "," +
                    duration + ",\'" +
                    description + "\'," +
                    Float.parseFloat(rating) + ",\'" +
                    country + "\'," +
                    "\'{\"" + genre + "\"}\'" +
                    "); ");

            statement.execute("INSERT INTO moviefinder.films_people(film, person, kind) VALUES (" +
                    id + "," +
                    producerId +
                    ",\'producer\'" +
                    "); ");
            statement.execute("INSERT INTO moviefinder.films_people(film, person, kind) VALUES (" +
                    id + "," +
                    actor1Id +
                    ",\'actor\'" +
                    "); ");
            statement.execute("INSERT INTO moviefinder.films_people(film, person, kind) VALUES (" +
                    id + "," +
                    actor2Id +
                    ",\'actor\'" +
                    "); ");
            statement.execute("INSERT INTO moviefinder.films_people(film, person, kind) VALUES (" +
                    id + "," +
                    actor3Id +
                    ",\'actor\'" +
                    "); ");
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return findFilm(name);
    }

    public static String createPerson(String name, String birthday,
                                      String country, String occupation) {
        String id;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM moviefinder.counters WHERE name=\'people\'; ");
            resultSet.next();
            id = resultSet.getString("next_id");
            statement.execute("UPDATE moviefinder.counters SET next_id=" +
                    (Integer.parseInt(id) + 1) +
                    " WHERE name=\'people\'; ");

            statement.execute("INSERT INTO moviefinder.people VALUES (" +
                    id + ",\'" +
                    name + "\',\'" +
                    StringToDateParser.parse(birthday) + "\',\'" +
                    country + "\'," +
                    "\'{\" " + occupation + " \"}\'" +
                    "); ");
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return findPerson(name);
    }

    public static String createReview(String mark, String date,
                                      String text, String filmId) {

        String id;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM moviefinder.counters WHERE name=\'reviews\'; ");
            resultSet.next();
            id = resultSet.getString("next_id");
            statement.execute("UPDATE moviefinder.counters SET next_id=" +
                    (Integer.parseInt(id) + 1) +
                    " WHERE name=\'reviews\'; ");

            statement.execute("INSERT INTO moviefinder.reviews VALUES (" +
                    id + ",\'" +
                    StringToDateParser.parse(date) + "\'," +
                    mark + ",\'" +
                    text + "\'" +
                    "); ");

            statement.execute("INSERT INTO moviefinder.films_reviews(film, review) VALUES (" +
                    filmId + "," +
                    id +
                    "); ");
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return findReview(id);
    }

    public static String findFilm(String name) {

        String result;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movieFinder.films " +
                    "WHERE name= \'" + name + "\';");
            resultSet.next();
            result = new JSONObject()
                    .put("responce_type", "ok")
                    .put("name", resultSet.getString("name"))
                    .put("year", resultSet.getInt("year"))
                    .put("duration", resultSet.getInt("duration"))
                    .put("descrition", resultSet.getString("description"))
                    .put("country", resultSet.getString("country"))
                    .put("rating", resultSet.getDouble("rating"))
                    .put("id", resultSet.getInt("id")).toString();
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return result;
    }

    public static String findPerson(String name) {
        String result;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movieFinder.people " +
                    "WHERE name= \'" + name + "\';");
            resultSet.next();
            result = new JSONObject().put("responce_type", "ok")
                    .put("name", resultSet.getString("name"))
                    .put("bithday", resultSet.getString("birthday"))
                    .put("country", resultSet.getString("country"))
                    .put("id", resultSet.getString("id")).toString();
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return result;
    }

    public static String findReview(String id) {
        String result;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movieFinder.reviews " +
                    "WHERE id= " + id + ";");
            resultSet.next();
            result = new JSONObject()
                    .put("responce_type", "ok")
                    .put("mark", resultSet.getString("mark"))
                    .put("date", resultSet.getString("time"))
                    .put("text", resultSet.getString("review"))
                    .put("id", resultSet.getString("id")).toString();
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
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
            statement.execute("UPDATE movieFinder.films " +
                    "SET name = \'" + newName + "\', " +
                    "year = " + newYear + ", " +
                    "duration = " + newDuration + ", " +
                    "country = \'" + newCountry + "\', " +
                    "rating = " + newRating + ", " +
                    "description = \'" + newDescription + "\', " +
                    "genres = \'{\"" + newGenre + "\"}\' " +
                    "WHERE id = " + id + "; ");

            statement.execute("UPDATE movieFinder.films_people " +
                    "SET person = " + newProducerId + ", " +
                    "kind = \'producer\' " +
                    "WHERE film = " + id + "; ");
            statement.execute("UPDATE movieFinder.films_people " +
                    "SET person = " + newActor1Id + ", " +
                    "kind = \'actor\' " +
                    "WHERE film = " + id + "; ");
            statement.execute("UPDATE movieFinder.films_people " +
                    "SET person = " + newActor2Id + ", " +
                    "kind = \'actor\' " +
                    "WHERE film = " + id + "; ");
            statement.execute("UPDATE movieFinder.films_people " +
                    "SET person = " + newActor3Id + ", " +
                    "kind = \'actor\' " +
                    "WHERE film = " + id + "; ");
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return findFilm(newName);
    }

    public static String editPerson(String id, String newName, String newBirthday,
                                    String newCountry, String newOccupation) {

        try {
            statement.execute("UPDATE movieFinder.people " +
                    "SET name = \'" + newName + "\', " +
                    "birthday = \'" + StringToDateParser.parse(newBirthday) + "\', " +
                    "country = \'" + newCountry + "\', " +
                    "occupations = \'{\"" + newOccupation + "\"}\' " +
                    "WHERE id = " + id + "; ");
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return findPerson(newName);
    }

    public static String editReview(String id, String newMark,
                                    String newDate, String newText, String newFilmId) {

        try {
            statement.execute("UPDATE movieFinder.reviews " +
                    "SET time = \'" + StringToDateParser.parse(newDate) + "\', " +
                    "mark = " + newMark + ", " +
                    "review = \'" + newText + "\' " +
                    "WHERE id = " + id + "; ");

            statement.execute("UPDATE movieFinder.films_reviews " +
                    "SET film = " + newFilmId + " " +
                    "WHERE review = " + id + "; ");
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return findReview(id);
    }

    public static String deleteFilm(String id) {
        try {
            statement.execute("DELETE FROM movieFinder.films " +
                    "WHERE id= " + id + ";");
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return id;
    }

    public static String deletePerson(String id) {
        try {
            statement.execute("DELETE FROM movieFinder.people " +
                    "WHERE id= " + id + ";");
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return id;
    }

    public static String deleteReview(String id) {
        try {
            statement.execute("DELETE FROM movieFinder.reviews " +
                    "WHERE id= " + id + ";");
        } catch (Exception e) {
            System.err.println("PostgreSQL exception");
            return null;
        }

        return id;
    }

}
