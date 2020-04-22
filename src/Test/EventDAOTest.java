package Test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model1.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EventDAOTest
{
    private Database db;
    private Event bestEvent;

    @BeforeEach
    public void setUp() throws Exception
    {
        db = new Database();
        bestEvent = new Event("5678", "harleymc", "1234", 89.0, 120.1, "United States", "Springville", "Birthday", 1997);
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception
    {
        Event compareTest = null;
        try
        {
            Connection conn = db.openConnection();
            EventDAO eDAO = new EventDAO(conn);
            eDAO.createEvent(bestEvent);
            compareTest = eDAO.readEvent(bestEvent.getEventID());
            db.closeConnection(false);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws Exception
    {
        boolean didItWork = true;
        try
        {
            Connection conn = db.openConnection();
            EventDAO eDAO = new EventDAO(conn);
            eDAO.createEvent(bestEvent);
            eDAO.createEvent(bestEvent);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        Event compareTest = bestEvent;
        try
        {
            Connection conn = db.openConnection();
            EventDAO eDAO = new EventDAO(conn);
            compareTest = eDAO.readEvent(bestEvent.getEventID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    @Test
    public void findPass() throws Exception
    {
        Event compareTest = null;
        try
        {
            Connection conn = db.openConnection();
            EventDAO eDAO = new EventDAO(conn);
            eDAO.createEvent(bestEvent);
            compareTest = eDAO.readEvent(bestEvent.getEventID());
            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void findFail() throws Exception
    {
        Event compareTest = bestEvent;
        try
        {
            Connection conn = db.openConnection();
            EventDAO eDAO = new EventDAO(conn);
            compareTest = eDAO.readEvent(bestEvent.getEventID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    @Test
    public void findAllPass() throws Exception
    {
        Event compareTest = bestEvent;
        Event second = new Event("9", "harleymc", "1234", 23.3, 125.5, "Mexico", "Mexico City", "Wedding", 2020);
        ArrayList<Event> allEvents = new ArrayList<>();
        try
        {
            Connection conn = db.openConnection();
            EventDAO eDAO = new EventDAO(conn);
            eDAO.createEvent(bestEvent);
            eDAO.createEvent(second);
            allEvents = eDAO.readAllEvents(bestEvent.getAssociatedUsername());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertEquals(allEvents.get(0), compareTest);
        assertEquals(allEvents.get(1), second);
    }

    @Test
    public void deletePass() throws Exception
    {
        EventDAO eDAO = null;
        try
        {
            Connection conn = db.openConnection();
            eDAO = new EventDAO(conn);
            eDAO.createEvent(bestEvent);
            eDAO.dropEventTable();
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertNull(eDAO.readEvent(bestEvent.getEventID()));
        db.closeConnection(true);
    }
}
