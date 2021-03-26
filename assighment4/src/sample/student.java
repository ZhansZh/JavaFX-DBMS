package sample;


public class student {
    private int ID;
    private String LastName;
    private String FirstName;
    private String Number;
    private String Gender;
    private String Email;

    public student(int ID, String LastName, String FirstName, String Number, String Gender, String Email) {
        this.ID = ID;
        this.LastName = LastName;
        this.FirstName = FirstName;
        this.Number = Number;
        this.Gender = Gender;
        this.Email = Email;
    }

    public int getID() {
        return ID;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getNumber() {
        return Number;
    }

    public String getGender() {
        return Gender;
    }

    public String getEmail() {
        return Email;
    }
}