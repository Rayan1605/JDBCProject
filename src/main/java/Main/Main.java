package Main;

import DataBaseImplement.DatabaseCrudOperation;
import DataBaseImplement.DatabaseInterface;
import Id.Id;

import java.util.*;

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
        int ch = 0;
        // using Database create a password identification system
        System.out.println("Which database do you want to enter\n");
        System.out.println("1. Orthodontist Clinic\n");
        System.out.println("2. Dental Department\n");
        System.out.println("Please enter the number here -> ");
        try {
            ch = myInput.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Please enter a valid number | 1 | 2\n");
            System.out.println("1. Orthodontist Clinic\n");
            System.out.println("2. Dental Department\n");
            databasetoEnter(); //Using Recursion to call the method again
            // if they write an invalid number
        }
        switch (ch) {
            case 1, 2 -> {
                DatabaseName = tables[ch - 1];
                VerifyDetails(); // checkingthePassword
            }
            default -> {
                System.out.println("Please enter a valid number | 1 | 2\n");
                databasetoEnter(); // Recursion again if they enter an invalid number
            }
        }
    }

    private static void VerifyDetails() {
        //If there is no password, then it will ask to create a password
        if (implement.GetPassword(DatabaseName).equals("")) {
            System.out.println("You need to enter a password\n");
            implement.SetPassword(WritePassword(), DatabaseName);
            System.out.println("You have successfully Added a Password\n");
            return;
        }

        System.out.println();
        System.out.println("To access the " + DatabaseName +
                " database, please enter the password \n");
        System.out.println("Enter Here -> ");
        String password = myInput.next();
        //Checking the Password

        if (Objects.equals(password, implement.GetPassword(DatabaseName))) {
            System.out.println("\n");
            System.out.println("You have successfully logged in the  "
                    + DatabaseName + " database\n");
        }
        else {

            //If typed wrong 3 more time then the application closes
            if (count ==3) {
                System.out.println("You have entered the wrong password 3 times");
                System.out.println("Please try again later\n");
                System.exit(0);
            }

            System.out.println("Wrong password entered");
            System.out.println("Please try again -> You currently have \n" + (3 - count) + " more tries");
            DelayTimer(2000);
            count++;
            VerifyDetails(); //using Recursion
        }

    }

    private static String WritePassword() {
        System.out.println("Please enter the password you want to set\n");
        String password = myInput.next();
        if (CheckPasswordStrength(password)) {
            return password;
        } else {
            System.out.println("Please enter a stronger password\n");
            return WritePassword();
        }
    }

    //To check if the password is strong enough
    private static boolean CheckPasswordStrength(String Password) {
        int count = 0;
        List<String> specialChar = Arrays.asList("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+");
        boolean[] checkList = new boolean[4];
        for (Character a : Password.toCharArray()) {
            if (Character.isUpperCase(a)) {
                checkList[0] = true;
            }
            else if (Character.isLowerCase(a)) {
                checkList[1] = true;
            }
            else if (Character.isDigit(a)) {
                checkList[2] = true;
            }
            else {
                for (String s : specialChar) {
                    if (a.toString().equals(s)) {
                        checkList[3] = true;
                    }
                }

            }

        }
        for (boolean b : checkList) {
            if (b) {
                count++;
            }
        }
        return count > 2;
    }


}
