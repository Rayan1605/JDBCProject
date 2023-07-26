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
    //Constructor
public Id() {

}
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
//This will be used to enter the id into the database
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

    //This is to add Id to the list however before doing so we need to check
    // if the id is already there
    //This class will only be used when creating a Patient
    public boolean addId(int id) {
        GettingTheId();
        boolean check;
        if (this.id.size() == 0) {
            SettingTheId(id);
            return true;
        }
        check = checkifIdisthere(id);
        if (!check) {
            SettingTheId(id);
            return true;
        }
        return false;
    }

    private boolean checkifIdisthere(int id) {
        if (this.id.size() == 0) {
            return false;
        }
        else {
            for (Integer integer : this.id) {
                if (integer == id) {
                    return true;
                }
            }
            return false;
        }
    }

    //This is to remove the id from the list
    //This class will only be used when deleting a Patient
    //Then we need to see if it is in the other table
    //If it is then we can't delete it
    public boolean removeIdfromList(String table,int identity,String [] tables) {
        boolean canweRemove;
        GettingTheId();

        try {
            canweRemove = IdExistInOtherTable(table,identity,tables); // if it exists, then we can't
            if(canweRemove){ // if we can then remove again
                for (int i = 0; i < this.id.size(); i++) {
                    if (this.id.get(i) == identity) {
                        this.id.remove(i);
                        DeleteTheId(identity);
                        return true;
                    }
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean IdExistInOtherTable(String table, int identity, String[] tables) {


    }
}

}
