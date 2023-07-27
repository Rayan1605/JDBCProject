package DataBaseImplement;

import Id.Id;
import Patient.Patient;

//So we are going to extend ID which mean we can use the public method from Id,
//and we are going to implement the DatabaseInterface which mean we
// have to use all the method
//
public class DatabaseCrudOperation extends Id implements DatabaseInterface {
    @Override
    public boolean createPatient(Patient patient, String DatabaseName) {
        return false;
    }

    @Override
    public void showAllPatient(String DatabaseName) {

    }

    @Override
    public void showPatientBasedonID(int id, String DatabaseName) {

    }

    @Override
    public Patient ImportPatient(int id) {
        return null;
    }

    @Override
    public boolean updatePatient(int id, String itemtoUpdate, String newValue, int index, String DatabaseName) {
        return false;
    }

    @Override
    public void deletePatient(int id, String DatabaseName, String[] TableNames) {

    }

    @Override
    public String GetPassword(String databaseName) {
        return null;
    }

    @Override
    public void SetPassword(String password, String databaseName) {

    }
}
