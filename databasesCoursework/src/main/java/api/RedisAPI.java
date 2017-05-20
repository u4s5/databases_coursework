package api;

import org.json.JSONObject;

import static crud.RedisCRUD.*;
import static spark.Spark.*;

public class RedisAPI {

    public static void create() {

        post("/redis/films", (request, response) -> {
            String result;
            result = createFilm(request.queryParams("name"),
                    request.queryParams("year"),
                    request.queryParams("duration"),
                    request.queryParams("country"),
                    request.queryParams("rating"),
                    request.queryParams("genre"),
                    request.queryParams("description"),
                    request.queryParams("producerId"),
                    request.queryParams("actor1Id"),
                    request.queryParams("actor2Id"),
                    request.queryParams("actor3Id"));

            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(201);
            return result;
        });

        post("/redis/people", (request, response) -> {
            String result;
            result = createPerson(request.queryParams("name"),
                    request.queryParams("birthday"),
                    request.queryParams("country"),
                    request.queryParams("occupation"));
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(201);
            return result;
        });

        post("/redis/reviews", (request, response) -> {
            String result;
            result = createReview(request.queryParams("author"),
                    request.queryParams("mark"),
                    request.queryParams("date"),
                    request.queryParams("text"),
                    request.queryParams("filmId"));
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(201);
            return result;
        });


        get("/redis/films", (request, response) -> {
            String result = findFilm(request.queryParams("name"));
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(200);
            return result;
        });

        get("/redis/people", (request, response) -> {
            String result = findPerson(request.queryParams("name"));
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(200);
            return result;
        });

        get("/redis/reviews", (request, response) -> {
            String result = findReview(request.queryParams("id"));
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(200);
            return result;
        });


        put("/redis/films", (request, response) -> {
            String result;
            try {
                result = editFilm(request.queryParams("id"),
                        request.queryParams("name"),
                        request.queryParams("year"),
                        request.queryParams("duration"),
                        request.queryParams("country"),
                        request.queryParams("rating"),
                        request.queryParams("genre"),
                        request.queryParams("description"),
                        request.queryParams("producerId"),
                        request.queryParams("actor1Id"),
                        request.queryParams("actor2Id"),
                        request.queryParams("actor3Id"));
            } catch (NumberFormatException e) {
                    response.status(404);
                    return new JSONObject().put("status", "error").toString();
            }
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(200);
            return result;
        });

        put("/redis/people", (request, response) -> {
            String result;
            try {
                result = editPerson(request.queryParams("id"),
                        request.queryParams("name"),
                        request.queryParams("birthday"),
                        request.queryParams("country"),
                        request.queryParams("occupation"));
            } catch (NumberFormatException e) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();

            }
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(200);
            return result;
        });

        put("/redis/reviews", (request, response) -> {
            String result;
            try {
                result = editReview(request.queryParams("id"),
                        request.queryParams("author"),
                        request.queryParams("mark"),
                        request.queryParams("date"),
                        request.queryParams("text"),
                        request.queryParams("filmId"));
            } catch (NumberFormatException e) {
                    response.status(404);
                    return new JSONObject().put("status", "error").toString();
            }
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(200);
            return result;
        });


        delete("/redis/films", (request, response) -> {
            String result;
            try {
                result = deleteFilm(request.queryParams("id"));
            } catch (NumberFormatException e) {
                    response.status(404);
                    return new JSONObject().put("status", "error").toString();
            }
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(200);
            return result;
        });

        delete("/redis/people", (request, response) -> {
            String result;
            try {
                result = deletePerson(request.queryParams("id"));
            } catch (NumberFormatException e) {
                    response.status(404);
                    return new JSONObject().put("status", "error").toString();
            }
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(200);
            return result;
        });

        delete("/redis/reviews", (request, response) -> {
            String result;
            try {
                result = deleteReview(request.queryParams("id"));
            } catch (NumberFormatException e) {
                    response.status(404);
                    return new JSONObject().put("status", "error").toString();
            }
            if (result == null) {
                response.status(404);
                return new JSONObject().put("status", "error").toString();
            }
            else
                response.status(200);
            return result;
        });

    }
}
