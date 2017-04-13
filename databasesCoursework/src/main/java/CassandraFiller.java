import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraFiller {

    public static void fill() {

        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.newSession();

        session.execute("CREATE KEYSPACE IF NOT EXISTS test WITH replication = {"
                + " 'class': 'SimpleStrategy', "
                + " 'replication_factor': '3' "
                + "};" );

        session.execute("CREATE TABLE test.Users (nickname text PRIMARY KEY, email text, password text, birthday date, registration_date date, country text, city text);");

    }

}
