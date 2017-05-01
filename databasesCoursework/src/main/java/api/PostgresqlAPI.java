package api;

import static crud.PostgresqlCRUD.*;
import static spark.Spark.*;

public class PostgresqlAPI {

    public static void create() {

        post("/postgreSQL/films", (request, response) -> {
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
            if (result == null)
                response.status(404);
            else
                response.status(201);
            return result;
        });

        post("/postgreSQL/people", (request, response) -> {
            String result;
            result = createPerson(request.queryParams("name"),
                    request.queryParams("birthday"),
                    request.queryParams("country"),
                    request.queryParams("occupation"));
            if (result == null)
                response.status(404);
            else
                response.status(201);
            return result;
        });

        post("/postgreSQL/reviews", (request, response) -> {
            String result;
            result = createReview(request.queryParams("mark"),
                    request.queryParams("date"),
                    request.queryParams("text"),
                    request.queryParams("filmId"));
            if (result == null)
                response.status(404);
            else
                response.status(201);
            return result;
        });


        get("/postgreSQL/films", (request, response) -> {
            String result = findFilm(request.queryParams("name"));
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });

        get("/postgreSQL/people", (request, response) -> {
            String result = findPerson(request.queryParams("name"));
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });

        get("/postgreSQL/reviews", (request, response) -> {
            String result = findReview(request.queryParams("id"));
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });


        put("/postgreSQL/films", (request, response) -> {
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
                return e.toString();
            }
            if (result == null)
                response.status(404);
            else
                response.status(200);
            return result;
        });

        put("/postgreSQL/people", (request, response) -> {
            String result;
            try {
                result = editPerson(request.queryParams("id"),
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

        put("/postgreSQL/reviews", (request, response) -> {
            String result;
            try {
                result = editReview(request.queryParams("id"),
                        request.queryParams("mark"),
                        request.queryParams("date"),
                        request.queryParams("text"),
                        request.queryParams("filmId"));
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


        delete("/postgreSQL/films", (request, response) -> {
            String result;
            try {
                result = deleteFilm(request.queryParams("id"));
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

        delete("/postgreSQL/people", (request, response) -> {
            String result;
            try {
                result = deletePerson(request.queryParams("id"));
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

        delete("/postgreSQL/reviews", (request, response) -> {
            String result;
            try {
                result = deleteReview(request.queryParams("id"));
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
