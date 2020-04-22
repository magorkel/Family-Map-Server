package Request1;

/**
 * gets all the information in that we need about the User to be able to create a User
 * just a bunch of getters and setters
 */
public class RegisterRequest
{
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender; //should just be a single letter though
    //private String personID;

    public RegisterRequest (String UserNameIn, String PasswordIn, String EmailIn, String FirstNameIn, String LastNameIn, String GenderIn/*, String PersonIDIn*/)
    {
        userName = UserNameIn;
        password = PasswordIn;
        email = EmailIn;
        firstName = FirstNameIn;
        lastName = LastNameIn;
        gender = GenderIn;
        //personID = PersonIDIn;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    /*public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }*/
}
