package Service;

import DataAccess.*;
import Model1.Event;
import Model1.Token;
import Model1.User;
import Response1.EventResponse;
import Response1.PersonResponse;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * check to make sure that the information being sent is actually usable
 * and deals with it appropriately
 */
public class EventService
{
    /**
     * returns all events for all the family members of the current user
     * @param authToken the person we want
     * @return specifies what happened, if the information was retrieved or if something went wrong
     */
    public EventResponse eventService (String authToken, String eventID)
    {
        Database db = new Database();
        EventResponse response;
        try
        {
            Connection conn = db.openConnection();
            TokenDAO tDAO = new TokenDAO(conn);
            EventDAO eDAO = new EventDAO(conn);

            ArrayList<Event> eventsList = new ArrayList<>();

            Token token = tDAO.readToken(authToken);
            if (token == null)
            {
                response = new EventResponse(null, "Error: no token was found matching the authToken", false);
                db.closeConnection(true);
                return response;
            }

            String userName = token.getUserName();

            if (eventID == null)
            {
                eventsList = eDAO.readAllEvents(userName);
                response = new EventResponse(eventsList, null, true);
            }
            else
            {
                Event event = eDAO.readEvent(eventID);
                if (event == null)
                {
                    response = new EventResponse(null, "Error: no event found matching userName", false);
                }
                else if (event.getAssociatedUsername().equals(userName))
                {
                    eventsList.add(event);
                    response = new EventResponse(eventsList, null, true);
                }
                else
                {
                    response = new EventResponse(null, "Error: no event found matching userName", false);
                }
            }

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            response = new EventResponse(null, "Invalid auth token, Internal server error", false);
        }

        return response;
    }
}
