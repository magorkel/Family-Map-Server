package Test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.TokenDAO;
import Model1.Token;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TokenDAOTest
{
    private Database db;
    private Token bestToken;

    @BeforeEach
    public void setUp() throws Exception
    {
        db = new Database();
        bestToken = new Token("harleymc", "3456", "helloworld");
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
        Token compareTest = null;
        try
        {
            Connection conn = db.openConnection();
            TokenDAO tDAO = new TokenDAO(conn);
            tDAO.createToken(bestToken);
            compareTest = tDAO.readToken(bestToken.getUniqueToken());
            db.closeConnection(false);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bestToken, compareTest);
    }

    @Test
    public void insertFail() throws Exception
    {
        boolean didItWork = true;
        try
        {
            Connection conn = db.openConnection();
            TokenDAO tDAO = new TokenDAO(conn);
            tDAO.createToken(bestToken);
            tDAO.createToken(bestToken);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        Token compareTest = bestToken;
        try
        {
            Connection conn = db.openConnection();
            TokenDAO tDAO = new TokenDAO(conn);
            compareTest = tDAO.readToken(bestToken.getUniqueToken());
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
        Token compareTest = null;
        try
        {
            Connection conn = db.openConnection();
            TokenDAO tDAO = new TokenDAO(conn);
            tDAO.createToken(bestToken);
            compareTest = tDAO.readToken(bestToken.getUniqueToken());
            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertEquals(bestToken, compareTest);
    }

    @Test
    public void findFail() throws Exception
    {
        Token compareTest = bestToken;
        try
        {
            Connection conn = db.openConnection();
            TokenDAO tDAO = new TokenDAO(conn);
            compareTest = tDAO.readToken(bestToken.getUniqueToken());
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
        TokenDAO tDAO = null;
        try
        {
            Connection conn = db.openConnection();
            tDAO = new TokenDAO(conn);
            tDAO.createToken(bestToken);
            tDAO.dropTokenTable();
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertNull(tDAO.readToken(bestToken.getUniqueToken()));
        db.closeConnection(true);
    }
}
