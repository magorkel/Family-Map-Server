package Request1;

/**
 * gets all the information in that we need about the User to be able to access that specific User
 * just a bunch of getters and setters
 */
public class LoginRequest
{
    String userName;
    String password;

    public LoginRequest (String UserNameIn, String PasswordIn)
    {
        userName = UserNameIn;
        password = PasswordIn;
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
}
