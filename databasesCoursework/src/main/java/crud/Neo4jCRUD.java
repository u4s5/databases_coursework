package crud;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Entity;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Neo4jCRUD {

    private static Session session;

    static {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687",
                AuthTokens.basic("neo4j", "qwer1234"));
        session = driver.session();
    }

    public static String createFilm(String name, String year, String duration,
                                    String country, String rating,
                                    String genre, String description,
                                    String producerId, String actor1Id,
                                    String actor2Id, String actor3Id) {
        int id;

        try {
            StatementResult statementResult = session.run("MATCH (f:Film) RETURN f ORDER BY f.id desc LIMIT 1;");
            Record record = statementResult.next();
            Value value = record.get("f");
            Value value2 = value.get("id");
            id = value2.asInt() + 1;

            session.run("CREATE (f:Film {" +
                    "id: " + id + ", " +
                    "name: \"" + name + "\", " +
                    "year: " + year + ", " +
                    "duration: " + duration + ", " +
                    "description: \"" + description + "\", " +
                    "rating: " + rating + ", " +
                    "genres: [\"" + genre + "\"], " +
                    "country: \"" + country + "\"});");

            session.run("MATCH (p:Person {id: " + producerId + "}), " +
                    "(f:Film {id: " + id + "}) " +
                    "MERGE (p)-[:DIRECTS]->(f);");

            session.run("MATCH (p:Person {id: " + actor1Id + "}), " +
                    "(f:Film {id: " + id + "}) " +
                    "MERGE (p)-[:ACTS_IN]->(f);");
            session.run("MATCH (p:Person {id: " + actor2Id + "}), " +
                    "(f:Film {id: " + id + "}) " +
                    "MERGE (p)-[:ACTS_IN]->(f);");
            session.run("MATCH (p:Person {id: " + actor3Id + "}), " +
                    "(f:Film {id: " + id + "}) " +
                    "MERGE (p)-[:ACTS_IN]->(f);");
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        return findFilm(name);
    }

    public static String createPerson(String name, String birthday,
                                      String country) {
        int id;

        try {
            StatementResult statementResult = session.run("MATCH (p:Person) RETURN f ORDER BY p.id desc LIMIT 1;");
            Record record = statementResult.next();
            Value value = record.get("p");
            Value value2 = value.get("id");
            id = value2.asInt() + 1;

            session.run("CREATE (p:Person {" +
                    "id: " + id + ", " +
                    "name: \"" + name + "\", " +
                    "birthday: \"" + birthday + "\", " +
                    "country: \"" + country + "\"});");
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        return findPerson(name);
    }

    public static String createReview(String mark, String date,
                                      String text, String filmId) {
        Integer id;

        try {
            StatementResult statementResult = session.run("MATCH (r:Review) RETURN r ORDER BY r.id desc LIMIT 1;");
            Record record = statementResult.next();
            Value value = record.get("r");
            Value value2 = value.get("id");
            id = value2.asInt() + 1;

            session.run("CREATE (r:Review {" +
                    "id: " + id + ", " +
                    "date: \"" + date + "\", " +
                    "mark: " + mark + ", " +
                    "review: \"" + text + "\"});");

            session.run("MATCH (r:Review {id: " + id + "}), " +
                    "(f:Film {id: " + filmId + "}) " +
                    "MERGE (p)-[:WRITTEN_FOR]->(f);");
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        return findReview(id.toString());
    }

    public static String findFilm(String name) {
        List<Record> res = new ArrayList<>();
        try {
            StatementResult statementResult = session.run("MATCH (f:Film {name: \"" + name + "\"}) RETURN f ;");
            while (statementResult.hasNext()){
                res.add(statementResult.next());
            }
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (Record r : res) {
            result.append(recordToString(r)).append("\n");
        }

        return result.toString();
    }

    public static String findPerson(String name) {
        List<Record> res = new ArrayList<>();
        try {
            StatementResult statementResult = session.run("MATCH (p:Person {name: \"" + name + "\"}) RETURN p ;");
            while (statementResult.hasNext()){
                res.add(statementResult.next());
            }
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (Record r : res) {
            result.append(recordToString(r)).append("\n");
        }

        return result.toString();
    }

    public static String findReview(String id) {
        List<Record> res = new ArrayList<>();
        try {
            StatementResult statementResult = session.run("MATCH (r:Review {id: " + id + "}) RETURN r ;");
            while (statementResult.hasNext()){
                res.add(statementResult.next());
            }
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (Record r : res) {
            result.append(recordToString(r)).append("\n");
        }

        return result.toString();
    }

    public static String editFilm(String id, String newName, String newYear,
                                  String newDuration, String newCountry,
                                  String newRating, String newGenre,
                                  String newDescription, String newProducerId,
                                  String newActor1Id, String newActor2Id,
                                  String newActor3Id) {
        try {
            deleteFilm(id);

            session.run("CREATE (f:Film {" +
                    "id: " + id + ", " +
                    "name: \"" + newName + "\", " +
                    "year: " + newYear + ", " +
                    "duration: " + newDuration + ", " +
                    "description: \"" + newDescription + "\", " +
                    "rating: " + newRating + ", " +
                    "genres: [\"" + newGenre + "\"], " +
                    "country: \"" + newCountry + "\"});");

            session.run("MATCH (p:Person {id: " + newProducerId + "}), " +
                    "(f:Film {id: " + id + "}) " +
                    "MERGE (p)-[:DIRECTS]->(f);");

            session.run("MATCH (p:Person {id: " + newActor1Id + "}), " +
                    "(f:Film {id: " + id + "}) " +
                    "MERGE (p)-[:ACTS_IN]->(f);");
            session.run("MATCH (p:Person {id: " + newActor2Id + "}), " +
                    "(f:Film {id: " + id + "}) " +
                    "MERGE (p)-[:ACTS_IN]->(f);");
            session.run("MATCH (p:Person {id: " + newActor3Id + "}), " +
                    "(f:Film {id: " + id + "}) " +
                    "MERGE (p)-[:ACTS_IN]->(f);");
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        return findFilm(newName);
    }

    public static String editPerson(String id, String newName, String newBirthday,
                                    String newCountry) {
        try {
            deletePerson(id);

            session.run("CREATE (p:Person {" +
                    "id: " + id + ", " +
                    "name: \"" + newName + "\", " +
                    "birthday: \"" + newBirthday + "\", " +
                    "country: \"" + newCountry + "\"});");
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        return findPerson(newName);
    }

    public static String editReview(String id, String newMark,
                                    String newDate, String newText, String newFilmId) {
        try {
            deleteReview(id);

            session.run("CREATE (r:Review {" +
                    "id: " + id + ", " +
                    "date: \"" + newDate + "\", " +
                    "mark: " + newMark + ", " +
                    "review: \"" + newText + "\"});");

            session.run("MATCH (r:Review {id: " + id + "}), " +
                    "(f:Film {id: " + newFilmId + "}) " +
                    "MERGE (p)-[:WRITTEN_FOR]->(f);");
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        return findReview(id);
    }

    public static String deleteFilm(String id) {
        try {
            session.run("MATCH (f:Film {id: " + id + "}) DETACH DELETE f ;");
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        return id;
    }

    public static String deletePerson(String id) {
        try {
            session.run("MATCH (p:Person {id: " + id + "}) DETACH DELETE p ;");
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        return id;
    }

    public static String deleteReview(String id) {
        try {
            session.run("MATCH (r:Review {id: " + id + "}) DETACH DELETE r ;");
        } catch (Exception e) {
            System.err.println("Neo4j exception");
            return null;
        }

        return id;
    }



    private static String recordToString(Record record){
        StringBuilder result = new StringBuilder();

        Entity e = record.values().iterator().next().asEntity();

        Iterator keyIterator = e.asMap().keySet().iterator();
        Iterator valIterator = e.asMap().values().iterator();

        while(valIterator.hasNext()) {
            result.append(keyIterator.next()).append(" ").
                    append(valIterator.next()).append("\n");
        }
        return result.toString();
    }
}
