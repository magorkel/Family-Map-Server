package Service;

import DataAccess.*;
import Request1.LoadRequest;
import Response1.LoadResponse;

import java.sql.Connection;

/**
 * check to make sure that the information being sent is actually usable
 * and deals with it appropriately
 */
public class LoadService
{
    /**
     * clears everything from the DB, then loads in the new information given
     * @param inputLoadRequest holds all the new information
     * @return specifies what happened, if the information was added or if something went wrong
     */
    public LoadResponse loadService (LoadRequest inputLoadRequest)
    {
        Database db = new Database();
        LoadResponse response;
        try
        {
            Connection conn = db.openConnection();
            db.clearTables();
            try
            {
                UserDAO uDAO = new UserDAO(conn);
                PersonDAO pDAO = new PersonDAO(conn);
                EventDAO eDAO = new EventDAO(conn);
                int x = 0;
                int y = 0;
                int z = 0;
                for (int i = 0; i < inputLoadRequest.getUsers().length; ++i)
                {
                    uDAO.createUser(inputLoadRequest.getUsers()[i]);
                    x++;
                }
                for (int i = 0; i < inputLoadRequest.getPersons().length; ++i)
                {
                    pDAO.createPerson(inputLoadRequest.getPersons()[i]);
                    y++;
                }
                for (int i = 0; i < inputLoadRequest.getEvents().length; ++i)
                {
                    eDAO.createEvent(inputLoadRequest.getEvents()[i]);
                    z++;
                }
                response = new LoadResponse("Successfully added " + x + " users, " + y + " persons, and " + z + " events to the database.", true);
                db.closeConnection(true);
            }
            catch (DataAccessException e)
            {
                response = new LoadResponse("Invalid request data (missing values, invalid values, etc.).", false);
                db.closeConnection(false);
            }
        }
        catch (DataAccessException e)
        {
            response = new LoadResponse("Internal server error.", false);
            e.printStackTrace();
        }
        return response;
    }
}
