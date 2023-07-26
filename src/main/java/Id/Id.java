package Id;

import DbConnection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Id {
    //This is the class that will all id in the database
    // it will check if the id is already in the database
    // and delete the id if it is already there
    private final List<Integer> id = new ArrayList<>();
    Connection con = DataBaseConnection.CreateConnectionTouserdetails();
    Connection connection = DataBaseConnection.createConnectionToTeethTreatment();
//This will be used to get the Id
    //But first we clear it so that we can get the latest bunch of  ids
    private void  GettingTheId()  {
        id.clear();
        String query="SELECT * FROM theid";
        try {
            Statement statement2 = con.createStatement();
            ResultSet result = statement2.executeQuery(query);
            while (result.next()) {
                this.id.add(result.getInt("Id"));
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }

    }
//This will be used to delete the id
private void DeleteTheId(int a ){

    try {
        String query="DELETE FROM theid WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, a);
        statement.executeUpdate();

    }  catch (SQLException e) {
        e.printStackTrace();
    }
}
//This will be used to set the id
    public void SettingTheId(int a ){
        String query="INSERT INTO theid VALUES (?)";
        try {
            PreparedStatement statement2 = con.prepareStatement(query);
            statement2.setInt(1,a );
            statement2.executeUpdate();

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
