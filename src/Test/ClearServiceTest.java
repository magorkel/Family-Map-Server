package Test;

import DataAccess.Database;
import Model1.User;
import Request1.RegisterRequest;
import Response1.ClearResponse;
import Service.ClearService;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearServiceTest
{
    private Database db;

    @BeforeEach
    public void setUp() throws Exception
    {
        try
        {
            db = new Database();
            RegisterService registerService = new RegisterService();
            RegisterRequest request = new RegisterRequest("harleymc", "hello", "harleymc@gmail.com", "Harley", "Christensen", "f");
            registerService.registerService(request);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void clearTest() throws Exception
    {
        ClearService clearService = new ClearService();
        ClearResponse clearResponse = null;
        try
        {
            clearResponse =  clearService.clearService();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        assertTrue(clearResponse.getSuccess());
        assertEquals(clearResponse.getMessage(), "Clear succeeded.");
    }
}
