package DataBaseImplement;

import Patient.Patient;

public interface DatabaseInterface {
    boolean createPatient(Patient patient, String DatabaseName,boolean check); //Used to create
    void showAllPatient(String DatabaseName); // show all Patient
    void showPatientBasedonID(int id, String DatabaseName); // show patient on id
    Patient ImportPatient(int id);//Import Patient from database
    //UPDATE
    boolean updatePatient (int id, String itemtoUpdate, String newValue, int index , String DatabaseName);
    //DELETE
    void deletePatient (int id,String DatabaseName,String[] TableNames);
    //GettingPassword
    String GetPassword (String databaseName);
    void SetPassword (String password, String databaseName);

}
