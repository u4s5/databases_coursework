import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RedisFiller {

    private static final int MOVIES_COUNT = 250000;
    private static final int PEOPLE_COUNT = 500000;
    private static final int REVIEWS_COUNT = 500000;

    public static void fill() {

        Random random = new Random();

        // connect to database
        Jedis jedis = new Jedis("localhost");
        jedis.flushAll();

        // fill people
        for (Integer i = 0; i < PEOPLE_COUNT; i++) {
            String randomName = RandomGenerator.generateName();

            jedis.hmset("person:" + i, new HashMap<String, String>() {{
                put("name", randomName);
                put("birthday", RandomGenerator.generateBirthday().toString());
                put("country", RandomGenerator.generateCountry());
            }});

            jedis.hset("people_names", randomName, i.toString());

            if (i < PEOPLE_COUNT / 2)
                jedis.sadd("person_occupations:" + i, "actor");
            else
                jedis.sadd("person_occupations:" + i, "producer");
        }

        // fill films
        for (Integer i = 0; i < MOVIES_COUNT; i++) {
            String randomName = RandomGenerator.generateName();

            jedis.hmset("film:" + i, new HashMap<String, String>() {{
                put("name", randomName);
                put("country", RandomGenerator.generateCountry());
                put("rating", RandomGenerator.generateRating().toString());
                put("year", RandomGenerator.generateYear().toString());
                put("duration", RandomGenerator.generateDuration().toString());
                put("description", RandomGenerator.generateText(150));
            }});

            jedis.hset("films_names", randomName, i.toString());

            for (int j = 0; j < 5; j++)
                jedis.sadd("film_actors:" + i, Integer.toString(random.nextInt(PEOPLE_COUNT / 2)));

            List<String> genres = RandomGenerator.generateGenres();
            for (String genre : genres) jedis.sadd("film_genres:" + i, genre);

            jedis.sadd("film_producers:" + i, Integer.toString(random.nextInt(PEOPLE_COUNT / 2) + PEOPLE_COUNT / 2));
        }

        // fill reviews
        for (Integer i = 0; i < REVIEWS_COUNT; i++) {
            jedis.hmset("review:" + i, new HashMap<String, String>() {{
                put("author", RandomGenerator.generateName());
                put("mark", Integer.toString((int) (Math.round(RandomGenerator.generateRating()))));
                put("date", RandomGenerator.generateDate().toString());
                put("text", RandomGenerator.generateText(300));
                put("film_id", Integer.toString(random.nextInt(MOVIES_COUNT)));
            }});
        }

    }

}
