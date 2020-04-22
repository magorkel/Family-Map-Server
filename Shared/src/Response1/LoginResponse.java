package Response1;

/**
 * returns specific information needed for the client to see about the User table (basically confirming who they are and if they could log in)
 * includes a success statement saying if the requested changes were made or if an error happened
 */
public class LoginResponse extends Response
{
    String authToken;
    String userName;
    String personID;

    public LoginResponse(String AuthTokenIn, String UserNameIn, String PersonIDIn, String message, Boolean success)
    {
        super(message, success);
        authToken = AuthTokenIn;
        userName = UserNameIn;
        personID = PersonIDIn;
    }

    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }
}
