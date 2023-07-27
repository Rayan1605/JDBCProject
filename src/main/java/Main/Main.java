package Main;

import DataBaseImplement.DatabaseCrudOperation;
import DataBaseImplement.DatabaseInterface;
import Id.Id;

import java.util.Scanner;

public class Main {
    static Scanner myInput = new Scanner(System.in);
    static String[] tables = {"orthodontistclinic", "dentaldepartment"};
    static DatabaseInterface implement = new DatabaseCrudOperation();
    static String DatabaseName;
    static int count = 0;
    static Id id = new Id();

    public static void main(String[] args) {
        boolean exitApplicaton = false;
        System.out.println("Welcome to Dental Clinic!\n");
        databasetoEnter();//Which department to enter the database
        do {
            //Delaying the message
            DelayTimer(2000);
            int CrudNumber = CrudOption(); // find which crud operation to do

            switch (CrudNumber) {
                case 1 -> createPatient();
                case 2 -> showAllPatient();
                case 3 -> showPatientById();
                case 4 -> updatePatient();
                case 5 -> deletePatient();
                case 6-> importPatient();
                case 7 -> main(args);
                case 8 -> {
                    System.out.println("Thank you for using the application\n");
                    System.out.println("The application will now close\n");
                    exitApplicaton = true;
                }
            }

        } while (!exitApplicaton) ;

    }

    private static void databasetoEnter() {

    }


}
