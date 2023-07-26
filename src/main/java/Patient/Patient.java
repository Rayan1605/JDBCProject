package Patient;

public class Patient {
    private int ID;
    private String Firstname;
    private String dateOfBirthday;
    private String dateOfTreatment;

    private String address;
    private boolean needspecialNeeds;

    private String typeOfTreatment;
    private int phoneNumber;
    private String email;
    private String LastName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getDateOfBirthday() {
        return dateOfBirthday;
    }

    public void setDateOfBirthday(String dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    public String getDateOfTreatment() {
        return dateOfTreatment;
    }

    public void setDateOfTreatment(String dateOfTreatment) {
        this.dateOfTreatment = dateOfTreatment;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "ID=" + ID +
                ", Firstname='" + Firstname + '\'' +
                ", dateOfBirthday='" + dateOfBirthday + '\'' +
                ", dateOfTreatment='" + dateOfTreatment + '\'' +
                ", address='" + address + '\'' +
                ", needspecialNeeds=" + needspecialNeeds +
                ", typeOfTreatment='" + typeOfTreatment + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", LastName='" + LastName + '\'' +
                '}';
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean NeedspecialNeeds() {
        return needspecialNeeds;
    }

    public void setNeedspecialNeeds(boolean needspecialNeeds) {
        this.needspecialNeeds = needspecialNeeds;
    }

    public String getTypeOfTreatment() {
        return typeOfTreatment;
    }

    public void setTypeOfTreatment(String typeOfTreatment) {
        this.typeOfTreatment = typeOfTreatment;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
