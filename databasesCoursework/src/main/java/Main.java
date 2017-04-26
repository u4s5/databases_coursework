import filling.PostgreSQLFiller;

public class Main {

    public static void main(String[] args) {
        //filling.MongoFiller.fill();
        //TimeTest.testMongo();

        //filling.RedisFiller.fill();
        //TimeTest.testRedis();

        //filling.CassandraFiller.fill();
        //TimeTest.testCassandra();

        //filling.Neo4jFiller.fill();
        //TimeTest.testNeo4j();

        PostgreSQLFiller.fill();
        TimeTest.testPostgreSQL();
    }

}
