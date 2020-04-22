package Test;

import DataAccess.Database;
import Request1.RegisterRequest;
import Response1.RegisterResponse;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest
{
    private Database db;

    @BeforeEach
    public void setUp() throws Exception
    {
        db = new Database();
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void registerTestPass() throws Exception
    {
        RegisterService registerService = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest("harleymc", "hello", "h@gmail.com","Harley", "Christensen", "f");
        RegisterResponse registerResponse = null;
        try
        {
            registerResponse = registerService.registerService(registerRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertTrue(registerResponse.getSuccess());
        assertNull(registerResponse.getMessage());
    }

    @Test
    public void registerTestFail() throws Exception
    {
        RegisterService registerService = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest("harleymc", "hello", "h@gmail.com","Harley", "Christensen", "f");
        RegisterResponse registerResponse = null;
        try
        {
            registerResponse = registerService.registerService(registerRequest);
            assertTrue(registerResponse.getSuccess());
            assertNull(registerResponse.getMessage());
            registerResponse = registerService.registerService(registerRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertFalse(registerResponse.getSuccess());
        assertEquals(registerResponse.getMessage(),"Request property missing or has invalid value, Username already taken by another user, Internal server error");
    }
}

