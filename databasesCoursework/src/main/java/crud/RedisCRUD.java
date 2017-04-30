package crud;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class RedisCRUD {

    private static Jedis jedis;

    static {
        jedis = new Jedis("redis-19168.c10.us-east-1-3.ec2.cloud.redislabs.com", 19168);
    }

    public static String createFilm(String name, String year, String duration,
                                    String country, String rating,
                                    String genre, String description,
                                    String producerId, String actor1Id,
                                    String actor2Id, String actor3Id) {
        try {
            String id = jedis.get("counters:films");
            jedis.hmset("film:" + id, new HashMap<String, String>() {{
                put("name", name);
                put("country", country);
                put("rating", rating);
                put("year", year);
                put("duration", duration);
                put("description", description);
            }});
            jedis.set("counters:films", String.valueOf(Integer.parseInt(id) + 1));

            jedis.sadd("film_genres:" + id, genre);

            jedis.hset("films_names", name, id);

            jedis.sadd("film_actors:" + id, actor1Id);
            jedis.sadd("film_actors:" + id, actor2Id);
            jedis.sadd("film_actors:" + id, actor3Id);

            jedis.sadd("film_producers:" + id, producerId);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        return findFilm(name);
    }

    public static String createPerson(String name, String birthday,
                                      String country, String occupation) {
        try {
            String id = jedis.get("counters:people");
            jedis.hmset("person:" + id, new HashMap<String, String>() {{
                put("name", name);
                put("birthday", birthday);
                put("country", country);
            }});
            jedis.set("counters:people", String.valueOf(Integer.parseInt(id) + 1));

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
        String id;
        try {
            id = jedis.get("counters:reviews");
            jedis.hmset("review:" + id, new HashMap<String, String>() {{
                put("author", author);
                put("mark", mark);
                put("date", date);
                put("text", text);
                put("film_id", filmId);
            }});
            jedis.set("counters:reviews", String.valueOf(Integer.parseInt(id) + 1));
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        return findReview(id);
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

        StringBuilder result = new StringBuilder();
        for (String o : map.values()) result.append(o).append(" ");

        return result.append(" id=").append(id).toString();
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

        StringBuilder result = new StringBuilder();
        for (String o : map.values()) result.append(o).append(" ");

        return result.append(" id=").append(id).toString();
    }

    public static String findReview(String id) {
        Map<String, String> map;

        try {
            map = jedis.hgetAll("review:" + id);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (String o : map.values()) result.append(o).append(" ");

        return result.append(" id=").append(id).toString();
    }

    public static String editFilm(String id, String newName, String newYear,
                                  String newDuration, String newCountry,
                                  String newRating, String newGenre,
                                  String newDescription, String newProducerId,
                                  String newActor1Id, String newActor2Id,
                                  String newActor3Id) {
        try {
            String oldName = jedis.hmget("film:" + id, "name").get(0);
            jedis.del("film:" + id);
            jedis.hmset("film:" + id, new HashMap<String, String>() {{
                put("name", newName);
                put("rating", newRating);
                put("country", newCountry);
                put("year", newYear);
                put("duration", newDuration);
                put("description", newDescription);
            }});

            jedis.del("film_genres:" + id);
            jedis.sadd("film_genres:" + id, newGenre);

            jedis.hdel("films_names", oldName);
            jedis.hset("films_names", newName, id);

            jedis.del("film_actors:" + id);
            jedis.sadd("film_actors:" + id, newActor1Id);
            jedis.sadd("film_actors:" + id, newActor2Id);
            jedis.sadd("film_actors:" + id, newActor3Id);

            jedis.del("film_producers:" + id);
            jedis.sadd("film_producers:" + id, newProducerId);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        return findFilm(newName);
    }

    public static String editPerson(String id, String newName, String newBirthday,
                                    String newCountry, String newOccupation) {

        try {
            String oldName = jedis.hmget("person:" + id, "name").get(0);
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

        try {
            jedis.del("review:" + id);
            jedis.hmset("review:" + id, new HashMap<String, String>() {{
                put("author", newAuthor);
                put("mark", newMark);
                put("date", newDate);
                put("film_id", newFilmId);
                put("text", newText);
            }});
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        return findReview(id);
    }

    public static String deleteFilm(String id) {
        try {
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

        return id;
    }

    public static String deletePerson(String id) {
        try {
            String oldName = jedis.hmget("person:" + id, "name").get(0);
            jedis.del("person:" + id);

            jedis.hdel("people_names", oldName);

            jedis.del("person_occupations:" + id);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        return id;
    }

    public static String deleteReview(String id) {
        try {
            jedis.del("review:" + id);
        } catch (Exception e) {
            System.err.println("Redis exception");
            return null;
        }

        return id;
    }

}
