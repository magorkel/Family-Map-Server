package Test;

import DataAccess.Database;
import DataAccess.EventDAO;
import DataAccess.TokenDAO;
import Model1.Event;
import Model1.Token;
import Response1.EventResponse;
import Service.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest
{
    private Database db;

    @BeforeEach
    public void setUp() throws Exception
    {
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();

        Token token = new Token("harleymc", "1234", "helloWorld");
        Event event1 = new Event("1", "harleymc", "1234", 23.3, 456.4, "USA", "Springville", "marriage", 2020);
        Event event2 = new Event("2", "harleymc", "1234", 445.5, 34.4, "Russia", "nowhere", "birthday", 2018);
        Event event3 = new Event("3", "jared", "5678", 10.1, 23.4, "USA", "Draper", "jared_birthday", 2016);

        TokenDAO tokenDAO = new TokenDAO(conn);
        tokenDAO.createToken(token);
        EventDAO eventDAO = new EventDAO(conn);
        eventDAO.createEvent(event1);
        eventDAO.createEvent(event2);
        eventDAO.createEvent(event3);

        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void eventTestPass() throws Exception
    {
        EventService eventService = new EventService();
        EventResponse eventResponse = null;
        try
        {
            eventResponse = eventService.eventService("helloWorld", null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertTrue(eventResponse.getSuccess());
        assertNull(eventResponse.getMessage());
    }

    @Test
    public void eventTestFail() throws Exception
    {
        EventService eventService = new EventService();
        EventResponse eventResponse = null;
        try
        {
            eventResponse = eventService.eventService("helloWorld", "jared_birthday");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertFalse(eventResponse.getSuccess());
        assertNotNull(eventResponse.getMessage());
    }
}
