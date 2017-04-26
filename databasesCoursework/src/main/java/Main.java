import filling.*;

public class Main {

    public static void main(String[] args) {
        //MongoFiller.fill();
        //TimeTest.testMongo();

        //RedisFiller.fill();
        //TimeTest.testRedis();

        //CassandraFiller.fill();
        //TimeTest.testCassandra();

        //Neo4jFiller.fill();
        //TimeTest.testNeo4j();

        PostgreSQLFiller.fill();
        TimeTest.testPostgreSQL();
    }

}
