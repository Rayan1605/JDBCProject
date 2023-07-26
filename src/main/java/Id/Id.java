package Id;

import DbConnection.DataBaseConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Id {
    //This is the class that will all id in the database
    // it will check if the id is already in the database
    // and delete the id if it is already there
    private final List<Integer> id = new ArrayList<>();
    Connection con = DataBaseConnection.CreateConnectionTouserdetails();
    Connection connection = DataBaseConnection.createConnectionToTeethTreatment();


}
