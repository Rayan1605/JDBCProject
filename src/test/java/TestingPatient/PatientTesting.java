package TestingPatient;

import Patient.Patient;
import org.junit.jupiter.api.BeforeEach;

public class PatientTesting {
Patient patient = new Patient();

     @BeforeEach
     public void Set() {
        patient.setID(1);
        patient.setFirstname("John");
        patient.setEmail("John@gmail.com");
        patient.setLastName("Smith");
        patient.setPhoneNumber(123456789);
        patient.setAddress("PoliceStation");

        patient.setDateOfBirthday("01/01/2000");
        patient.setDateOfTreatment("01/01/2020");
        patient.setAddress("LeasNaCoille");
        patient.setNeedspecialNeeds(true);
        patient.setTypeOfTreatment("Filling");
    }
}
