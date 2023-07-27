package Main;

import DataBaseImplement.DatabaseCrudOperation;
import DataBaseImplement.DatabaseInterface;
import Id.Id;
import Patient.Patient;

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

    private static void importPatient() {
        System.out.println("What the id of the person you wish to import");
        Patient patient = implement.ImportPatient(myInput.nextInt());
        implement.createPatient(patient,DatabaseName);
    }

    private static void deletePatient() {
        System.out.println("Enter ID: ");
        int id = myInput.nextInt();
        implement.deletePatient(id, DatabaseName,tables);
    }


    private static void showPatientById() {
        System.out.println("Enter ID: ");
        int id = myInput.nextInt();
        implement.showPatientBasedonID(id, DatabaseName);
    }

    private static void showAllPatient() {
        implement.showAllPatient(DatabaseName);
        DelayTimer(2000);
    }


    private static int CrudOption() {
        System.out.println("The options are as follows\n");
        DelayTimer(300);
        System.out.println();
        System.out.println("1. Create Patient\n");
        DelayTimer(600);
        System.out.println("2. Show All Patient\n");
        DelayTimer(600);
        System.out.println("3. Show Patient by Id\n");
        DelayTimer(600);
        System.out.println("4. Update Patient\n");
        DelayTimer(600);
        System.out.println("5. Delete Patient\n");
        DelayTimer(600);
        System.out.println("6. Import Patient\n");
        DelayTimer(600);
        System.out.println("7. Change Database\n");
        DelayTimer(600);
        System.out.println("8. Exit Application \n");
        DelayTimer(600);
        System.out.println(" Please enter your choice below\n");
        System.out.println("Enter Here -> ");
        DelayTimer(600);
        int CrudOption = myInput.nextInt();
        if (CrudOption > 0 && CrudOption <= 7) {
            return CrudOption;
        } else {
            System.out.println("Please enter a valid number | 1 | 2 | 3 | 4 | 5 | 6| 7 \n");
            System.out.println("The options are as follows\n");
            return CrudOption();
        }
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
                VerifyDetails(); // checking-thePassword
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
//This will just delay the program for how long we decide to set it for
    private static void DelayTimer(int num) {
        try {
            Thread.sleep(num);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
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

    private static void createPatient() {
        Patient pat = new Patient();
        System.out.println("Enter ID: ");
        pat.setID(myInput.nextInt());
        System.out.println("Enter First Name: ");
        pat.setFirstname( myInput.next());
        System.out.println("Enter Last Name: ");
        pat.setLastName( myInput.next());
        System.out.println("Enter email");
        pat.setEmail( myInput.next());
        System.out.println("Enter Phone Number: ");
        pat.setPhoneNumber( myInput.nextInt());
        System.out.println("Enter DOB: ");
        pat.setDateOfBirthday( myInput.next());
        System.out.println("Enter Treatment Date: ");
        pat.setDateOfTreatment( myInput.next());
        System.out.println("Enter Address: ");
        pat.setAddress(myInput.next());
        System.out.println("Is Special Needs?: true or false ");
        pat.setNeedspecialNeeds(CheckIfItBoolean(myInput.next()));
        System.out.println("Enter Treatment Type: ");
        pat.setTypeOfTreatment( myInput.next());

        boolean check = implement.createPatient(pat, DatabaseName);
        if (check){
            System.out.println("It been added Successfully\n");
        }
        else {
            System.out.println("Therefore it has not been added Successfully\n");
        }


    }

    private static boolean CheckIfItBoolean(String nextBoolean) {
        if(nextBoolean.equalsIgnoreCase("true")){
            return true;
        }
        else if(nextBoolean.equalsIgnoreCase("false")){
            return false;
        }
        else{
            System.out.println("Please enter a valid boolean value -> true or false\n");
            CheckIfItBoolean(nextBoolean);
        }
        return false;
    }

    private static void updatePatient() {

        System.out.println("Enter the ID of the Patient you would like to change : ");
        int idToUpdate = myInput.nextInt();
        //Checking if the ID exists in the database and the method to check is in
        // Id class
        if (!id.DoesIdExistInTable(DatabaseName, idToUpdate)){
            //If the ID does not exist in the database, then it will ask to enter a valid ID

            System.out.println("The ID you have entered does not exist in the database");
            System.out.println("Please enter a valid ID");
            System.out.println("You have " + (3 - count) + " tries left");
            updatePatient();
        }
        String[] updateOptions = {"DateofBirth", "DateofTreatment"
                , "Address", "NeedSpecialNeeds", "TypeOfTreatment,", "PhoneNumber","Email", };
        int updateOption = WhichOptiontoUpdate(updateOptions) - 1;
        //Once we have the option we want to update,
        //We will send it to the updatePatient method
        switch (updateOption) {
            case 0 -> {
                System.out.println("Enter DateOfBirth: ");
                implement.updatePatient(idToUpdate, updateOptions[updateOption],
                        myInput.next(), 4, DatabaseName);
            }
            case 1 -> {
                System.out.println("Enter DateOfTreatment: ");
                implement.updatePatient(idToUpdate, updateOptions[updateOption],
                        myInput.next(), 5, DatabaseName);
            }
            case 2 -> {
                System.out.println("Enter New Address: ");
                implement.updatePatient(idToUpdate, updateOptions[updateOption],
                        myInput.next(), 6, DatabaseName);
            }
            case 3 -> {
                System.out.println("Enter  if you require NeedSpecialNeed: true or false: ");
                implement.updatePatient(idToUpdate, updateOptions[updateOption],
                        myInput.next(), 7, DatabaseName);
            }
            case 4 -> {
                System.out.println("Enter  the TypeOfTreatment: ");
                implement.updatePatient(idToUpdate, updateOptions[updateOption],
                        myInput.next(), 8, DatabaseName);
            }

            case 5-> {
                System.out.println("Enter new  PhoneNumber: ");
                implement.updatePatient(idToUpdate, updateOptions[updateOption],
                        myInput.next(), 9, DatabaseName);
            }
            case 6-> {
                System.out.println("Enter  the Email: ");
                implement.updatePatient(idToUpdate, updateOptions[updateOption],
                        myInput.next(), 10, DatabaseName);
            }
            default -> System.out.println("ERROR!");
        }

    }

    private static int WhichOptiontoUpdate(String[] updateOptions) {
        System.out.println("Which of the options would you like to Update?\n");
        int counter = 1;
        for(String option : updateOptions){
            System.out.println(counter + ". " +  option);
            counter++;
        }
        System.out.println("Please Enter: ");
        int input = myInput.nextInt();
        if(input > 0 && input <= 7){
            return input;
        }
        //If they don't enter a valid number
        //We will use recursion to call the method again so they can enter it again
        else{
            System.out.println("Please enter a valid number | 1 | 2 | 3 | 4 | 5| 6 | 7 \n");
            return WhichOptiontoUpdate(updateOptions);
        }
    }


}
