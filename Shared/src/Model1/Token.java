package Model1;

import java.util.Objects;

/**
 * the Token object
 * constructor and getters and setters
 */
public class Token
{
    String userName;
    String password;
    String uniqueToken;

    public Token(String userName, String password, String uniqueToken)
    {
        this.userName = userName;
        this.password = password;
        this.uniqueToken = uniqueToken;
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

    public String getUniqueToken()
    {
        return uniqueToken;
    }

    public void setUniqueToken(String uniqueToken)
    {
        this.uniqueToken = uniqueToken;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return userName.equals(token.userName) &&
                password.equals(token.password) &&
                uniqueToken.equals(token.uniqueToken);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(userName, password, uniqueToken);
    }
}
