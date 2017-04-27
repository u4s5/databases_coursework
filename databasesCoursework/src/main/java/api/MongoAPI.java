package api;

import static crud.MongoCRUD.createFilm;
import static spark.Spark.post;

public class MongoAPI {

    public static void create() {

        post("/mongo/films", (request, responce) -> {
            String result;
            try {
                result = createFilm(request.queryParams("name"),
                        Integer.parseInt(request.queryParams("year")),
                        Integer.parseInt(request.queryParams("duration")),
                        request.queryParams("country"),
                        Double.parseDouble(request.queryParams("rating")),
                        request.queryParams("genre"), request.queryParams("description"),
                        Integer.parseInt(request.queryParams("producerId")),
                        Integer.parseInt(request.queryParams("actor1Id")),
                        Integer.parseInt(request.queryParams("actor2Id")),
                        Integer.parseInt(request.queryParams("actor3Id")));
            } catch (NumberFormatException e) {
                responce.status(404);
                return e.toString();
            }
            if (result == null)
                responce.status(404);
            else
                responce.status(201);
            return result;
        });


    }

}
