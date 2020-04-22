package Test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model1.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest
{
    private Database db;
    private User bestUser;

    @BeforeEach
    public void setUp() throws Exception
    {
        db = new Database();
        bestUser = new User("harleymc", "hello", "harley@gmail.com", "Harley", "Christensen", "f", "1234");
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
        User compareTest = null;
        try
        {
            Connection conn = db.openConnection();
            UserDAO uDAO = new UserDAO(conn);
            uDAO.createUser(bestUser);
            compareTest = uDAO.readUser(bestUser.getUserName());
            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }
    @Test
    public void insertFail() throws Exception
    {
        boolean didItWork = true;
        try
        {
            Connection conn = db.openConnection();
            UserDAO uDAO = new UserDAO(conn);
            uDAO.createUser(bestUser);
            uDAO.createUser(bestUser);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        User compareTest = bestUser;
        try
        {
            Connection conn = db.openConnection();
            UserDAO uDAO = new UserDAO(conn);
            compareTest = uDAO.readUser(bestUser.getUserName());
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
        User compareTest = null;
        try
        {
            Connection conn = db.openConnection();
            UserDAO uDAO = new UserDAO(conn);
            uDAO.createUser(bestUser);
            compareTest = uDAO.readUser(bestUser.getUserName());
            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void findFail() throws Exception
    {
        User compareTest = bestUser;
        try
        {
            Connection conn = db.openConnection();
            UserDAO uDAO = new UserDAO(conn);
            compareTest = uDAO.readUser(bestUser.getUserName());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    @Test
    public void deletePass() throws Exception
    {
        UserDAO uDAO = null;
        try
        {
            Connection conn = db.openConnection();
            uDAO = new UserDAO(conn);
            uDAO.createUser(bestUser);
            uDAO.dropUserTable();
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertNull(uDAO.readUser(bestUser.getPersonID()));
        db.closeConnection(true);
    }
}
