package Test;

import DataAccess.Database;
import DataAccess.UserDAO;
import Model1.Person;
import Model1.User;
import Response1.FillResponse;
import Service.FillService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest
{
    private Database db;

    @BeforeEach
    public void setUp() throws Exception
    {
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();
        User user = new User("harleymc", "hello", "h@gmail.com", "Harley", "Christensen", "f", "1234");
        UserDAO userDAO = new UserDAO(conn);
        userDAO.createUser(user);
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
    public void fillTestPass() throws Exception
    {
        FillService fillService = new FillService();
        FillResponse fillResponse = null;
        try
        {
            fillResponse = fillService.fillService("harleymc", 2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertTrue(fillResponse.getSuccess());
        assertEquals(fillResponse.getMessage(), "Successfully added 7 persons and 19 events to the database.");
    }

    @Test
    public void fillTestFail() throws Exception
    {
        FillService fillService = new FillService();
        FillResponse fillResponse = null;
        try
        {
            fillResponse = fillService.fillService("jared", 4);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertFalse(fillResponse.getSuccess());
        assertEquals(fillResponse.getMessage(), "Invalid userName, does not exist");
    }
}
