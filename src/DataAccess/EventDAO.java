package DataAccess;

import Model1.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * makes changes to the database based on the information sent
 */
public class EventDAO
{
    private final Connection conn;
    public EventDAO(Connection conn) {this.conn = conn;}
    /**
     * create
     * creates an event and inserts it into the Event table
     * @param event the event we want added
     * @throws DataAccessException exception
     */
    public void createEvent(Event event) throws DataAccessException
    {
        String sql = "INSERT INTO Event (EventID, AssociatedUsername, PersonID, Latitude, Longitude, Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
    /**
     * read
     * reads just a single row from the event table
     * @param eventID specifies which row
     * @return returns just the row we want
     * @throws DataAccessException exception
     */
    public Event readEvent(String eventID) throws DataAccessException
    {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE EventID = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"), rs.getString("PersonID"), rs.getDouble("Latitude"), rs.getDouble("Longitude"), rs.getString("Country"), rs.getString("City"), rs.getString("EventType"), rs.getInt("Year"));
                return event;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        }
        finally
        {
            if (rs != null)
            {
                try {rs.close();}
                catch (SQLException e) {e.printStackTrace();}
            }
        }
        return null;
    }
    public ArrayList<Event> readEventFromPerson(String personID) throws DataAccessException
    {
        ArrayList<Event> event = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE PersonID = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                event.add(new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"), rs.getString("PersonID"), rs.getDouble("Latitude"), rs.getDouble("Longitude"), rs.getString("Country"), rs.getString("City"), rs.getString("EventType"), rs.getInt("Year")));
            }
            return event;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        }
        finally
        {
            if (rs != null)
            {
                try {rs.close();}
                catch (SQLException e) {e.printStackTrace();}
            }
        }
    }
    /**
     * readAll
     * reads all the events stored in the Event table
     * @return returns the whole table
     * @throws DataAccessException exception
     */
    public ArrayList<Event> readAllEvents(String AssociatedUsername) throws DataAccessException
    {
        ArrayList<Event> allEvents = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, AssociatedUsername);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                allEvents.add(new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"), rs.getString("PersonID"), rs.getDouble("Latitude"), rs.getDouble("Longitude"), rs.getString("Country"), rs.getString("City"), rs.getString("EventType"), rs.getInt("Year")));
            }
            return allEvents;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        }
        finally
        {
            if (rs != null)
            {
                try {rs.close();}
                catch (SQLException e) {e.printStackTrace();}
            }
        }
    }
    /**
     * delete
     * deletes the row that the ID specifies
     * @param eventID specifies which row
     * @throws DataAccessException exception
     */
    public void deleteEvent(String eventID) throws DataAccessException
    {
        String sql = "DELETE FROM Event WHERE EventID = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, eventID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing event");
        }
    }
    public void deleteEventFromUser(String userName) throws DataAccessException
    {
        String sql = "DELETE FROM Event WHERE AssociatedUsername = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, userName);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing event");
        }
    }
    /**
     * drops the whole event table
     * @throws DataAccessException exception
     */
    public void dropEventTable() throws DataAccessException
    {
        String sql = "DELETE FROM Event";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing all events");
        }
    }
}
