package api;

public class API {

    public static void create() {
        MongoAPI.create();
        RedisAPI.create();
        CassandraAPI.create();
        Neo4jAPI.create();
        PostgresqlAPI.create();
    }

}
