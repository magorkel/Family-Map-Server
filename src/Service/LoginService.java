package Service;

import DataAccess.*;
import Model1.Person;
import Model1.Token;
import Model1.User;
import Request1.LoginRequest;
import Response1.LoginResponse;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.UUID;

/**
 * check to make sure that the information being sent is actually usable
 * and deals with it appropriately
 */
public class LoginService
{
    private String authToken;

    /**
     * depending on what we want done with the information, it will call the specific DAO function
     * if it is usable information, send to the TokenDAO
     * else return error response
     * @param inputLoginRequest holds all the login information for the token
     * @return specifies what happened, if the information was retrieved or if something went wrong
     */
    public LoginResponse loginService(LoginRequest inputLoginRequest)
    {
        Database db = new Database();
        LoginResponse lr;
        boolean success = false;
        try
        {
            Connection conn = db.openConnection();
            String userName = inputLoginRequest.getUserName();
            String password = inputLoginRequest.getPassword();
            UserDAO uDAO = new UserDAO(conn);
            PersonDAO pDAO = new PersonDAO(conn);
            Person person = pDAO.readPersonWithUsername(userName);
            if (person == null)
            {
                lr = new LoginResponse(null, null, null, "Request property missing or has invalid value, Internal server error", false);
                db.closeConnection(false);
                return lr;
            }
            String personID = person.getPersonID();//cant handle being returned null?, breaks out and starts try over in handler
            User currentUser = uDAO.readUser(userName);
            if (userName.equals(currentUser.getUserName()))
            {
                if (password.equals(currentUser.getPassword()))
                {
                    success = true;
                }
            }
            if(success)
            {
                authToken = UUID.randomUUID().toString();
                TokenDAO tDAO = new TokenDAO(conn);
                tDAO.deleteTokenFromUsername(userName);
                Token token = new Token(userName, password, authToken);
                tDAO.createToken(token);
                lr = new LoginResponse(authToken, userName, personID, null, true);
            }
            else
            {
                lr = new LoginResponse(null, null, null, "Error: Request property missing or has invalid value", false);
            }
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            lr = new LoginResponse(null, null, null, "Request property missing or has invalid value", false);
            try
            {
                db.closeConnection(false);
            } catch (DataAccessException ex)
            {
                lr = new LoginResponse(null, null, null, "Internal server error", false);
                ex.printStackTrace();
            }
        }
        return lr;
    }
}
