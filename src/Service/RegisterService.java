package Service;

import DataAccess.*;
import Model1.Person;
import Model1.Token;
import Model1.User;
import Request1.RegisterRequest;
import Response1.RegisterResponse;

import java.sql.Connection;
import java.util.UUID;

/**
 * check to make sure that the information being sent is actually usable
 * and deals with it appropriately
 */
public class RegisterService
{
    /**
     * depending on what we want done with the information, it will call the specific DAO function.
     * if it is usable, send to UserDAO
     * else return error response
     * @param inputRegisterRequest holds all the information about the user
     * @return specifies what happened, if the information was added/changed or if something went wrong
     */
    public RegisterResponse registerService(RegisterRequest inputRegisterRequest)
    {
        Database db = new Database();
        RegisterResponse response;
        try
        {
            Connection conn = db.openConnection();

            String userName = inputRegisterRequest.getUserName();
            String password = inputRegisterRequest.getPassword();
            String email = inputRegisterRequest.getEmail();
            String firstName = inputRegisterRequest.getFirstName();
            String lastName = inputRegisterRequest.getLastName();
            String gender = inputRegisterRequest.getGender();
            String personID = UUID.randomUUID().toString();
            String authToken = UUID.randomUUID().toString();

            UserDAO uDAO = new UserDAO(conn);
            PersonDAO pDAO = new PersonDAO(conn);
            TokenDAO tDAO = new TokenDAO(conn);

            User user = new User(userName, password, email, firstName, lastName, gender, personID);
            Person person = new Person(personID, userName, firstName, lastName, gender, null, null, null);
            Token token = new Token(userName, password, authToken);

            if (uDAO.readUser(userName) == null)
            {
                uDAO.createUser(user);
                pDAO.createPerson(person);
                tDAO.createToken(token);

                //if (pDAO.readPerson(personID) == null) { pDAO.createPerson(person); }
                //if (tDAO.readToken(authToken) == null) { tDAO.createToken(token); }

                db.closeConnection(true);

                FillService fillService = new FillService();
                fillService.fillService(userName, 4);

                response = new RegisterResponse(authToken, userName, personID, null, true);
            }
            else
            {
                db.closeConnection(false);
                response = new RegisterResponse(null, null, null, "Request property missing or has invalid value, Username already taken by another user, Internal server error", false);
            }
        }
        catch(DataAccessException e)
        {
            response = new RegisterResponse(null, null, null, "Request property missing or has invalid value, Username already taken by another user, Internal server error", false);
            try
            {
                db.closeConnection(false);
            } catch (DataAccessException ex)
            {
                ex.printStackTrace();
            }
        }
        return response;
    }
}
