package crud;

import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.*;

import static utils.CustomFileReader.readFile;

public class RedisCRUD {

    private static Jedis jedis;

    static {
        jedis = new Jedis("localhost");
    }

    public static String createFilm(String name, String year, String duration,
                                    String country, String rating,
                                    String genre, String description,
                                    String producerId, String actor1Id,
                                    String actor2Id, String actor3Id) {

        Jedis jedis = new Jedis("localhost");
        Object result;

        try {
            result = jedis.eval(readFile("upsert_film.lua"), 12, name, String.valueOf(year),
                    String.valueOf(duration), country, String.valueOf(rating), genre, description,
                    String.valueOf(producerId), String.valueOf(actor1Id), String.valueOf(actor2Id),
                    String.valueOf(actor3Id), "-1");

            if(result instanceof Long && (Long) result == -1){
                System.err.println("Redis insert error");
                return null;
            }
        }
        catch (IOException e){
            System.err.println("IO exception");
            return null;
        };
        return findFilm(name);
    }

    public static String createPerson(String name, String birthday,
                                      String country, String occupation) {

        String id;

        try {
            id = jedis.keys("person:*").size() + "";
            jedis.hmset("person:" + id, new HashMap<String, String>() {{
                put("name", name);
                put("birthday", birthday);
                put("country", country);
            }});

            jedis.hset("people_names", name, id);

            jedis.sadd("person_occupations:" + id, occupation);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }
        return findPerson(name);
    }

    public static String createReview(String author, String mark, String date,
                                      String text, String filmId) {

        Jedis jedis = new Jedis("localhost");
        Object result = null;

        try {
            result = jedis.eval(readFile("upsert_review.lua"), 6, author, mark, date, text, filmId, "-1");
            if(result instanceof Long && (Long) result == -1){
                return null;
            }
        }
        catch (IOException e){};

        return findReview(String.valueOf(result));
    }

    public static String findFilm(String name) {
        Map<String, String> map;
        String id;

        try {
            id = jedis.hget("films_names", name);
            map = jedis.hgetAll("film:" + id);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        if(map.size() == 0)
            return new JSONObject().put("status", "error").toString();

        return getJSON(map).put("status", "ok").toString();
    }

    public static String findPerson(String name) {
        Map<String, String> map;
        String id;

        try {
            id = jedis.hget("people_names", name);
            map = jedis.hgetAll("person:" + id);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        if(map.size() == 0)
            return new JSONObject().put("status", "error").toString();

        return getJSON(map).put("status", "ok").toString();
    }

    public static String findReview(String id) {
        Map<String, String> map;

        try {
            map = jedis.hgetAll("review:" + id);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        if(map.size() == 0)
            return new JSONObject().put("status", "error").toString();

        return getJSON(map).put("status", "ok").toString();
    }

    public static String editFilm(String id, String newName, String newYear,
                                  String newDuration, String newCountry,
                                  String newRating, String newGenre,
                                  String newDescription, String newProducerId,
                                  String newActor1Id, String newActor2Id,
                                  String newActor3Id) {
        try {
                Jedis jedis = new Jedis("localhost");
                Object result;

                result = jedis.eval(readFile("upsert_film.lua"), 12, newName, String.valueOf(newYear),
                        String.valueOf(newDuration), newCountry, String.valueOf(newRating), newGenre, newDescription,
                        String.valueOf(newProducerId), String.valueOf(newActor1Id), String.valueOf(newActor2Id),
                        String.valueOf(newActor3Id), id);

                if (result instanceof Long && (Long) result == -1) {
                    System.err.println("Redis insert error");
                    return null;
                }
            } catch (IOException e) {
                System.err.println("IO exception");
                return null;
            }
            return findFilm(newName);

    }

    public static String editPerson(String id, String newName, String newBirthday,
                                    String newCountry, String newOccupation) {

        try {
            String oldName = jedis.hmget("film:" + id, "name").get(0);
            jedis.del("person:" + id);
            jedis.hmset("person:" + id, new HashMap<String, String>() {{
                put("name", newName);
                put("birthday", newBirthday);
                put("country", newCountry);
            }});

            jedis.hdel("people_names", oldName);
            jedis.hset("people_names", newName, id);

            jedis.del("person_occupations:" + id);
            jedis.sadd("person_occupations:" + id, newOccupation);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        return findPerson(newName);
    }

    public static String editReview(String id, String newAuthor, String newMark,
                                    String newDate, String newText, String newFilmId) {

        Jedis jedis = new Jedis("localhost");
        Object result = null;

        try {
            result = jedis.eval(readFile("upsert_review.lua"), 6, newAuthor, newMark, newDate, newText, newFilmId, id);
            if(result instanceof Long && (Long) result == -1){
                return null;
            }
        }
        catch (IOException e){};

        return new JSONObject(findReview(String.valueOf(result))).put("id", String.valueOf(result)).toString();
    }

    public static String deleteFilm(String id) {
        Map<String, String> map;
        try {
            map = jedis.hgetAll("film:" + id);
            if(map.size() == 0)
                return new JSONObject().put("status", "error").toString();

            String oldName = jedis.hmget("film:" + id, "name").get(0);
            jedis.del("film:" + id);

            jedis.del("film_genres:" + id);

            jedis.hdel("films_names", oldName);

            jedis.del("film_actors:" + id);

            jedis.del("film_producers:" + id);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        return getJSON(map).put("status", "ok").toString();
    }

    public static String deletePerson(String id) {
        Map<String, String> map;
        try {
            map = jedis.hgetAll("person:" + id);
            if(map.size() == 0)
                return new JSONObject().put("status", "error").toString();

            String oldName = jedis.hmget("person:" + id, "name").get(0);
            jedis.del("person:" + id);

            jedis.hdel("people_names", oldName);

            jedis.del("person_occupations:" + id);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        return getJSON(map).put("status", "ok").toString();
    }

    public static String deleteReview(String id) {
        Map<String, String> map;
        try {
            map = jedis.hgetAll("review:" + id);
            if(map.size() == 0)
                return new JSONObject().put("status", "error").toString();

            jedis.del("review:" + id);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        return getJSON(map).put("status", "ok").toString();
    }

    private static JSONObject getJSON(Map<String, String> map){
        JSONObject json = new JSONObject();

        Object[] values = map.values().toArray();
        Object[] keys = map.keySet().toArray();

        for(int i = 0; i < values.length; i++){
            json.put((String)keys[i], values[i]);
        }

        return json;
    }
}
