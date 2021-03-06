package crud;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public class MongoCRUD {

    private static MongoDatabase db;

    static {
        MongoClientURI uri = new MongoClientURI("mongodb://u4s5:qwer1234" +
                "@moviecluster-shard-00-00-zfyol.mongodb.net:27017," +
                "moviecluster-shard-00-01-zfyol.mongodb.net:27017," +
                "moviecluster-shard-00-02-zfyol.mongodb.net:27017/movieFinder" +
                "?ssl=true&replicaSet=MovieCluster-shard-0&authSource=admin");
        MongoClient mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase("movieFinder");
    }

    public static String createFilm(String name, int year, int duration,
                                    String country, double rating,
                                    String genre, String description,
                                    int producerId, int actor1Id,
                                    int actor2Id, int actor3Id) {

        MongoCollection collection = db.getCollection("counters");
        Document document = (Document) (collection.findOneAndUpdate(
                eq("_id", "filmId"),
                new Document("$inc", new Document("next", 1))));
        Iterator iterator = document.values().iterator();
        iterator.next();
        String str = iterator.next().toString();
        Integer id = Integer.valueOf(str.substring(0,str.length()-2));

        collection = db.getCollection("films");
        try {
            collection.insertOne(new Document("_id", id)
                    .append("name", name)
                    .append("year", year)
                    .append("duration", duration)
                    .append("countries", new ArrayList<String>() {{
                        add(country);
                    }})
                    .append("rating", rating)
                    .append("genres", new ArrayList<String>() {{
                        add(genre);
                    }})
                    .append("description", description)
                    .append("producer_ids", new ArrayList<Integer>() {{
                        add(producerId);
                    }})
                    .append("actor_ids", new ArrayList<Integer>() {{
                        add(actor1Id);
                        add(actor2Id);
                        add(actor3Id);
                    }}));
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        Object o = collection.find(eq("_id", id)).iterator().next();
        return  o.toString();
    }

    public static String createPerson(String name, String birthday,
                                      String country, String occupation) {

        MongoCollection collection = db.getCollection("counters");
        Document document = (Document) (collection.findOneAndUpdate(
                eq("_id", "personId"),
                new Document("$inc", new Document("next", 1))));
        Iterator iterator = document.values().iterator();
        iterator.next();
        Integer id = Integer.valueOf(iterator.next().toString());

        collection = db.getCollection("people");
        try {
            collection.insertOne(new Document("_id", id)
                    .append("name", name)
                    .append("birthday", StringToDateParser.parse(birthday))
                    .append("country", country)
                    .append("occupations", new ArrayList<String>() {{
                        add(occupation);
                    }}));
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        return collection.find(eq("_id", id)).iterator().next().toString();
    }

    public static String createReview(String author, int mark, String date,
                                      String text, int filmId) {

        MongoCollection collection = db.getCollection("counters");
        Document document = (Document) (collection.findOneAndUpdate(
                eq("_id", "reviewId"),
                new Document("$inc", new Document("next", 1))));
        Iterator iterator = document.values().iterator();
        iterator.next();
        Integer id = Integer.valueOf(iterator.next().toString());

        collection = db.getCollection("reviews");
        try {
            collection.insertOne(new Document("_id", id)
                    .append("author", author)
                    .append("mark", mark)
                    .append("date", StringToDateParser.parse(date))
                    .append("text", text)
                    .append("film_id", filmId));
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        return collection.find(eq("_id", id)).iterator().next().toString();
    }

    public static List<String> findFilm(String name) {
        MongoCollection collection = db.getCollection("films");
        MongoCursor cursor;

        try {
            cursor = collection.find(eq("name", name)).iterator();
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        if (!cursor.hasNext())
            return null;

        return new ArrayList<String>() {{
            while (cursor.hasNext())
                add(cursor.next().toString());
        }};
    }

    public static List<String> findPerson(String name) {
        MongoCollection collection = db.getCollection("people");
        MongoCursor cursor;

        try {
            cursor = collection.find(eq("name", name)).iterator();
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        if (!cursor.hasNext())
            return null;

        return new ArrayList<String>() {{
            while (cursor.hasNext())
                add(cursor.next().toString());
        }};
    }

    public static List<String> findReview(String author) {
        MongoCollection collection = db.getCollection("reviews");
        MongoCursor cursor;

        try {
            cursor = collection.find(eq("author", author)).iterator();
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        if (!cursor.hasNext())
            return null;

        return new ArrayList<String>() {{
            while (cursor.hasNext())
                add(cursor.next().toString());
        }};
    }

    public static String editFilm(int id, String newName, int newYear,
                                  int newDuration, String newCountry,
                                  double newRating, String newGenre,
                                  String newDescription, int newProducerId,
                                  int newActor1Id, int newActor2Id,
                                  int newActor3Id) {

        MongoCollection collection = db.getCollection("films");
        UpdateResult result;

        try {
            result = collection.updateOne(Filters.and(eq("_id", id)),
                    new Document("$set", new Document("_id", id)
                            .append("name", newName)
                            .append("year", newYear)
                            .append("duration", newDuration)
                            .append("countries", new ArrayList<String>() {{
                                add(newCountry);
                            }})
                            .append("rating", newRating)
                            .append("genres", new ArrayList<String>() {{
                                add(newGenre);
                            }})
                            .append("description", newDescription)
                            .append("producer_ids", new ArrayList<Integer>() {{
                                add(newProducerId);
                            }})
                            .append("actor_ids", new ArrayList<Integer>() {{
                                add(newActor1Id);
                                add(newActor2Id);
                                add(newActor3Id);
                            }})));
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        return result.toString();
    }

    public static String editPerson(int id, String newName, String newBirthday,
                                    String newCountry, String newOccupation) {

        MongoCollection collection = db.getCollection("people");
        UpdateResult result;

        try {
            result = collection.updateOne(Filters.and(eq("_id", id)),
                    new Document("$set", new Document("_id", id)
                            .append("name", newName)
                            .append("birthday", StringToDateParser.parse(newBirthday))
                            .append("country", newCountry)
                            .append("occupations", new ArrayList<String>() {{
                                add(newOccupation);
                            }})));
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        return result.toString();
    }

    public static String editReview(int id, String newAuthor, int newMark,
                                    String newDate, String newText, int newFilmId) {

        MongoCollection collection = db.getCollection("reviews");
        UpdateResult result;

        try {
            result = collection.updateOne(Filters.and(eq("_id", id)),
                    new Document("$set", new Document("_id", id)
                            .append("author", newAuthor)
                            .append("mark", newMark)
                            .append("date", StringToDateParser.parse(newDate))
                            .append("text", newText)
                            .append("film_id", newFilmId)));
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        return result.toString();
    }

    public static String deleteFilm(int id) {

        MongoCollection collection = db.getCollection("films");
        DeleteResult result;

        try {
            result = collection.deleteOne(eq("_id", id));
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        return result.toString();
    }

    public static String deletePerson(int id) {

        MongoCollection collection = db.getCollection("people");
        DeleteResult result;

        try {
            result = collection.deleteOne(eq("_id", id));
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        return result.toString();
    }

    public static String deleteReview(int id) {

        MongoCollection collection = db.getCollection("reviews");
        DeleteResult result;

        try {
            result = collection.deleteOne(eq("_id", id));
        } catch (Exception e) {
            System.err.println("Mongo exception");
            return null;
        }

        return result.toString();
    }

}
