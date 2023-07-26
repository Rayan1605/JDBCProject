package DbConnection;

import java.sql.Connection;

public class DataBaseConnection {
    static Connection connection;

    public static Connection createConnectionToTeethTreatment() {
        return connection;
    }
}
