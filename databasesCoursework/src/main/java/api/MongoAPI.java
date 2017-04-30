package api;

import static crud.MongoCRUD.*;
import static spark.Spark.*;

public class MongoAPI {

    public static void create() {

        post("/mongo/films", (request, response) -> {
            String result;
            try {
                result = createFilm(request.queryParams("name"),
                        Integer.parseInt(request.queryParams("year")),
                        Integer.parseInt(request.queryParams("duration")),
                        request.queryParams("country"),
                        Double.parseDouble(request.queryParams("rating")),
                        request.queryParams("genre"),
                        request.queryParams("description"),
                        Integer.parseInt(request.queryParams("producerId")),
                        Integer.parseInt(request.queryParams("actor1Id")),
                        Integer.parseInt(request.queryParams("actor2Id")),
                        Integer.parseInt(request.queryParams("actor3Id")));
            } catch (NumberFormatException e) {
                response.status(404);
                return e.toString();
            }
            if (result == null)
                response.status(404);
            else
                response.status(201);
            return result;
        });

        post("/mongo/people", (request, response) -> {
            String result;
            try {
                result = createPerson(request.queryParams("name"),
                        request.queryParams("birthday"),
                        request.queryParams("country"),
                        request.queryParams("occupation"));
            } catch (NumberFormatException e) {
                response.status(404);
                return e.toString();
            }
            if (result == null)
                response.status(404);
            else
                response.status(201);
            return result;
        });

        post("/mongo/reviews", (request, response) -> {
            String result;
            try {
                result = createReview(request.queryParams("author"),
                        Integer.parseInt(request.queryParams("mark")),
                        request.queryParams("date"),
                        request.queryParams("text"),
                        Integer.parseInt(request.queryParams("filmId")));
            } catch (NumberFormatException e) {
                response.status(404);
                return e.toString();
            }
            if (result == null)
                response.status(404);
            else
                response.status(201);
            return result;
        });


        get("/mongo/films", (request, response) -> {
            String result = findFilm(request.queryParams("name")).get(0);
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });

        get("/mongo/people", (request, response) -> {
            String result = findPerson(request.queryParams("name")).get(0);
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });

        get("/mongo/reviews", (request, response) -> {
            String result = findReview(request.queryParams("author")).get(0);
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });


        put("/mongo/films", (request, response) -> {
            String result;
            try {
                result = editFilm(Integer.parseInt(request.queryParams("id")),
                        request.queryParams("name"),
                        Integer.parseInt(request.queryParams("year")),
                        Integer.parseInt(request.queryParams("duration")),
                        request.queryParams("country"),
                        Double.parseDouble(request.queryParams("rating")),
                        request.queryParams("genre"),
                        request.queryParams("description"),
                        Integer.parseInt(request.queryParams("producerId")),
                        Integer.parseInt(request.queryParams("actor1Id")),
                        Integer.parseInt(request.queryParams("actor2Id")),
                        Integer.parseInt(request.queryParams("actor3Id")));
            } catch (NumberFormatException e) {
                response.status(404);
                return e.toString();
            }
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });

        put("/mongo/people", (request, response) -> {
            String result;
            try {
                result = editPerson(Integer.parseInt(request.queryParams("id")),
                        request.queryParams("name"),
                        request.queryParams("birthday"),
                        request.queryParams("country"),
                        request.queryParams("occupation"));
            } catch (NumberFormatException e) {
                response.status(404);
                return e.toString();
            }
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });

        put("/mongo/reviews", (request, response) -> {
            String result;
            try {
                result = editReview(Integer.parseInt(request.queryParams("id")),
                        request.queryParams("author"),
                        Integer.parseInt(request.queryParams("mark")),
                        request.queryParams("date"),
                        request.queryParams("text"),
                        Integer.parseInt(request.queryParams("filmId")));
            } catch (NumberFormatException e) {
                response.status(404);
                return e.toString();
            }
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });


        delete("/mongo/films", (request, response) -> {
            String result;
            try {
                result = deleteFilm(Integer.parseInt(request.queryParams("id")));
            } catch (NumberFormatException e) {
                response.status(404);
                return e.toString();
            }
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });

        delete("/mongo/people", (request, response) -> {
            String result;
            try {
                result = deletePerson(Integer.parseInt(request.queryParams("id")));
            } catch (NumberFormatException e) {
                response.status(404);
                return e.toString();
            }
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });

        delete("/mongo/reviews", (request, response) -> {
            String result;
            try {
                result = deleteReview(Integer.parseInt(request.queryParams("id")));
            } catch (NumberFormatException e) {
                response.status(404);
                return e.toString();
            }
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });

    }
}
