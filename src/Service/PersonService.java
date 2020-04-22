package Service;

import DataAccess.*;
import Model1.Person;
import Model1.Token;
import Response1.PersonResponse;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * check to make sure that the information being sent is actually usable
 * and deals with it appropriately
 */
public class PersonService
{
    /**
     * returns all the family members from the current user
     * @param personID the person we want the information from
     * @return specifies what happened, if the information was retrieved or if something went wrong
     */
    public PersonResponse personService (String authToken, String personID)
    {
        Database db = new Database();
        PersonResponse response;
        try
        {
            Connection conn = db.openConnection();
            TokenDAO tDAO = new TokenDAO(conn);
            PersonDAO pDAO = new PersonDAO(conn);

            ArrayList<Person> personList = new ArrayList<>();

            Token token = tDAO.readToken(authToken);//need a case where is says if token is null, return a statement that says hey there's nothing here
            if (token == null)
            {
                response = new PersonResponse(null, "Error: no token was found matching the authToken", false);
                db.closeConnection(true);
                return response;
            }

            String userName = token.getUserName();

            if (personID == null)
            {
                personList = pDAO.readAllPersons(userName);
                response = new PersonResponse(personList, null, true);
            }
            else
            {
                Person person = pDAO.readPerson(personID);
                if (person.getAssociatedUsername().equals(userName))
                {
                    personList.add(person);
                    response = new PersonResponse(personList, null, true);
                }
                else
                {
                    response = new PersonResponse(null, "Error: No person associated with this userName", false);
                }
            }

            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            response = new PersonResponse(null, "Invalid auth token, Internal server error", false);
        }
        return response;
    }
}
