package Response1;

/**
 * returns specific information needed for the client to see about the User table
 * includes a success statement saying if the requested changes were made or if an error happened
 */
public class RegisterResponse extends Response
{
    String authToken;
    String userName;
    String personID;

    public RegisterResponse (String UniqueTokenIn, String UserNameIn, String PersonIDIn, String message, Boolean success)
    {
        super(message, success);
        authToken = UniqueTokenIn;
        userName = UserNameIn;
        personID = PersonIDIn;
    }

    public String getUniqueToken()
    {
        return authToken;
    }

    public void setUniqueToken(String uniqueToken)
    {
        authToken = uniqueToken;
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
