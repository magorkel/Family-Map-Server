package Test;

import DataAccess.Database;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Model1.Person;
import Model1.User;
import Request1.LoginRequest;
import Response1.LoginResponse;
import Service.LoginService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest
{
    private Database db;

    @BeforeEach
    public void setUp() throws Exception
    {
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();

        User user = new User("harleymc", "hello", "h@gmail.com", "Harley", "Christensen", "f", "1234");
        Person person = new Person("1234", "harleymc", "Harley", "Christensen", "f", null, null, null);

        UserDAO userDAO = new UserDAO(conn);
        PersonDAO personDAO = new PersonDAO(conn);

        userDAO.createUser(user);
        personDAO.createPerson(person);

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
    public void loginTestPass() throws Exception
    {
        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest("harleymc", "hello");
        LoginResponse loginResponse = null;
        try
        {
            loginResponse = loginService.loginService(loginRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertTrue(loginResponse.getSuccess());
        assertNull(loginResponse.getMessage());
    }

    @Test
    public void loginTestFail() throws Exception
    {
        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest("jared", "hello");
        LoginResponse loginResponse = null;
        try
        {
            loginResponse = loginService.loginService(loginRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertFalse(loginResponse.getSuccess());
        assertNotNull(loginResponse.getMessage());
    }
}
