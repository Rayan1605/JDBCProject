package DataBaseImplement;

import DbConnection.DataBaseConnection;
import Id.Id;
import Patient.Patient;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

//So we are going to extend ID which mean we can use the public method from ID,
//and we are going to implement the DatabaseInterface which mean we
// have to use all the method
//
public class DatabaseCrudOperation extends Id implements DatabaseInterface {
    Connection con;
    @Override
    public boolean createPatient(Patient patient, String DatabaseName) {
        if (!addId(patient.getID())) { // checking if we can add the id if
            // another id exist, ask them if they want to import it
            int choice = 0;
            do {
                System.out.println("The id is already there");
                System.out.println("Do you wish to import it from the other table");
                System.out.println("Press 1 for yes and 2 for no\n");

                Scanner myinput = new Scanner(System.in);
                System.out.println("Enter here -> \n");
                choice = myinput.nextInt();
                if (choice == 1) {
                    patient = ImportPatient(patient.getID());
                } else if (choice == 2) {
                    System.out.println("Please try again later\n");
                    return false;
                }
            } while (choice != 1);
            //If the user does not exist, then we are going to create a new one
            con= DataBaseConnection.createConnectionToTeethTreatment();
            String query="INSERT INTO " + DatabaseName + " VALUES (?,?,?,?,?,?,?,?,?,?)";
            //Then inserting it into the database
            try{
                PreparedStatement pst=con.prepareStatement(query);
                pst.setInt( 1, patient.getID());
                pst.setString( 2, patient.getFirstname());
                pst.setString( 3, patient.getLastName());
                pst.setString( 4, patient.getDateOfBirthday());
                pst.setString( 5, patient.getDateOfTreatment());
                pst.setString( 6, patient.getAddress());
                pst.setBoolean( 7, patient.NeedspecialNeeds());
                pst.setString( 8, patient.getTypeOfTreatment());
                pst.setInt( 9, patient.getPhoneNumber());
                pst.setString( 10, patient.getEmail());
                int cnt = pst.executeUpdate();
                return cnt != 0;

            }catch(Exception ex){
                return false;
            }
        }
        return false;
    }
//This method is used to show all the patient
    @Override
    public void showAllPatient(String DatabaseName) {
        con= DataBaseConnection.createConnectionToTeethTreatment();
        String query="SELECT * FROM " + DatabaseName; //getting everything
        System.out.println("Patient details: ");
////printing it in this format
        System.out.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n",
                "ID|", "FirstName","LastName|", "DateofBirth|", "DateofTreatment|", "Address|"
                , "NeedsSpecialNeeds|", "TypeOfTreatment|", "PhoneNumber|", "Email|");
        System.out.println("------------------------------------------------------------------------------------------------------------------\n");

        try{
            Statement stm2 =con.createStatement();
            ResultSet result= stm2.executeQuery(query);
            while(result.next()){
                ////match the format
                System.out.format("%d\t%s\t%s\t%s\t%s\t%s\t%b\t%s\t%d\t%s\n\n",
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getBoolean(7),
                        result.getString(8),
                        result.getInt(9),
                        result.getString(10));

                System.out.println("------------------------------------------------------------------------------------------------------------------\n");

            }
            System.out.println("\n");

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void showPatientBasedonID(int id, String DatabaseName) {
        //printing it in this format
        System.out.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n",
                "ID|", "FirstName","LastName|", "DateofBirth|", "DateofTreatment|",
                "Address|"
                , "NeedsSpecialNeeds|", "TypeOfTreatment|", "PhoneNumber|", "Email|");
        System.out.println("------------------------------------------------------------------------------------------------------------------\n");
        con = DataBaseConnection.createConnectionToTeethTreatment();
        String query = "SELECT * FROM " + DatabaseName + " where id = ?";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id); // so at the "?" it will be the id
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                System.out.format("%d\t%s\t%s\t%s\t%s\t%s\t%b\t%s\t%d\t%s\n\n",
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getBoolean(7),
                        result.getString(8),
                        result.getInt(9),
                        result.getString(10));

                System.out.println("------------------------------------------------------------------------------------------------------------------\n");

            } else {
                System.out.println("The id is not there");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Patient ImportPatient(int id) {
        Scanner myinput = new Scanner(System.in);
        System.out.println("Please enter the database name in which the patient is stored");
        String[] tables = {"orthodontistclinic", "dentaldepartment"};
        for (String names: tables) {
            //// printing the names of the tables
            System.out.println("the names are " + names + "\n");
        }
        System.out.println("Enter here ->  \n");
        //getting the name of the database
        String DatabaseName = myinput.next();
        //creating the connection
        con = DataBaseConnection.createConnectionToTeethTreatment();
        for (String table: tables) {
            if (table.equals(DatabaseName)) { // if they picked the option
                try {
                    //selecting it from the database
                    String query = "SELECT * FROM " + DatabaseName + " where id = ?";
                    PreparedStatement statement = con.prepareStatement(query);
                    statement.setInt(1, id);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        Patient patient = new Patient();
                        patient.setID(result.getInt(1)); //inserting it into the patient class
                        patient.setFirstname(result.getString(2));
                        patient.setLastName(result.getString(3));
                        patient.setDateOfBirthday(result.getString(4));
                        patient.setAddress(result.getString(6));
                        patient.setNeedspecialNeeds(result.getBoolean(7));
                        patient.setPhoneNumber(result.getInt(9));
                        patient.setEmail(result.getString(10));
                        return GetRemainingDetail(patient); //since it not in the same department
                        //We can assume that the date and type of treatment is not the same,
                        // so we will ask the user to enter it

                        //And we will save it to the patient class and return it

                    }else{
                        System.out.println("The patient is not in the database"); //if the patient is not in the database
                    }
                }catch (Exception e){
                    System.out.println("The patient is not in the database");
                }
                break;
            }
        }
        return null;
    }
///Just to get the remaining details by asking the user
    private Patient GetRemainingDetail(Patient patientClass) {
        System.out.println("Please enter the Patient Treatment Date\n");
        Scanner myinput = new Scanner(System.in);
        System.out.println("Enter here -> ");
        String date = myinput.nextLine();
        patientClass.setDateOfTreatment(date);
        System.out.println("Please enter the Patient Treatment Type\n");
        System.out.println("Enter here -> ");
        String type = myinput.nextLine();
        patientClass.setTypeOfTreatment(type);
        return patientClass;
    }

    @Override
    public boolean updatePatient(int id, String itemtoUpdate, String newValue, int index, String DatabaseName) {
        int count;
        con = DataBaseConnection.createConnectionToTeethTreatment();
        String query = "UPDATE " + DatabaseName + " SET " + itemtoUpdate + " = ? WHERE id = ?";
        //So it will update the item to Update to the newValue where the id is the id
        PreparedStatement statement;

        try {
            statement = con.prepareStatement(query);
            if (Objects.equals(itemtoUpdate, "phonenumber")) {
                for (Character c : newValue.toCharArray()) { // making sure the values are
                    //Number using ascii table
                    if (c < 48 || c > 57) {
                        System.out.println("Please enter a number");
                        return false;
                    }
                }
                statement.setInt(1, Integer.parseInt(newValue)); // so the first
                // is the new value
                statement.setInt(2, id);// second is the id
                count =  statement.executeUpdate();

            } else if (Objects.equals(itemtoUpdate, "needspecialNeeds")) {
//If it is a boolean then we will convert it to boolean
                    statement.setBoolean(1, Boolean.parseBoolean(newValue));
                    //then convert to boolean
                    statement.setInt(2, id);
                    count= statement.executeUpdate();

            } else {
                //If it is not a number or a boolean then it will be a string
                statement.setString(1, newValue);
                statement.setInt(2, id);
                count =  statement.executeUpdate();
            }
            if (count > 0) {
                System.out.println("Employee updated successfully");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deletePatient(int id, String DatabaseName, String[] TableNames) {
        con = DataBaseConnection.createConnectionToTeethTreatment();
        String query = "DELETE FROM  " + DatabaseName + " where id = ?";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt( 1, id);
            int cnt = pst.executeUpdate();
            if (cnt!=0) {
                System.out.println("Patient deleted!");
                //We need to remove it from the list since it will be
                // removed from the database,
                //However, just like it explained in the id class
                //we need to first see if this patient exists elsewhere
                removeIdfromList(DatabaseName,id,TableNames);
            }
            else {
                System.out.println("Patient not found!");//If it can't be found
                // then we assume it does not exist
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    //Just Getting the list of the tables
    @Override
    public String GetPassword(String databaseName) {
        con = DataBaseConnection.CreateConnectionTouserdetails();
        PreparedStatement statement;
        try {
            statement = con.prepareStatement("SELECT Password FROM " + databaseName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //It it found then it will return the password
                return resultSet.getString("Password");
            } else {
                //If it is not found then it will return an empty string
                return "";
            }
        } catch (SQLException e) {
            return "";
        }
    }

    @Override
    //if the password is not found, then it will create a new password
    public void SetPassword(String password, String databaseName) {
        con = DataBaseConnection.CreateConnectionTouserdetails();
        String query = "INSERT INTO " + databaseName + " (password) VALUES (?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,password);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
