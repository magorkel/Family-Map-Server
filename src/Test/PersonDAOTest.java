package Test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model1.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonDAOTest
{
    private Database db;
    private Person bestPerson;

    @BeforeEach
    public void setUp() throws Exception
    {
        db = new Database();
        bestPerson = new Person("1234", "harleymc", "Harley", "Christensen", "f", "5", "6", "7");
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
        Person compareTest = null;
        try
        {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            pDAO.createPerson(bestPerson);
            compareTest = pDAO.readPerson(bestPerson.getPersonID());
            db.closeConnection(false);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws Exception
    {
        boolean didItWork = true;
        try
        {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            pDAO.createPerson(bestPerson);
            pDAO.createPerson(bestPerson);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        Person compareTest = bestPerson;
        try
        {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            compareTest = pDAO.readPerson(bestPerson.getPersonID());
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
        Person compareTest = null;
        try
        {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            pDAO.createPerson(bestPerson);
            compareTest = pDAO.readPerson(bestPerson.getPersonID());
            db.closeConnection(true);
        }
        catch(DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void findFail() throws Exception
    {
        Person compareTest = bestPerson;
        try
        {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            compareTest = pDAO.readPerson(bestPerson.getPersonID());
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
        Person compareTest = bestPerson;
        Person second = new Person("5678", "harleymc", "Shaelyn", "Christensen", "f", "5", "6", "8");
        ArrayList<Person> allPeople = new ArrayList<>();
        try
        {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            pDAO.createPerson(bestPerson);
            pDAO.createPerson(second);
            allPeople = pDAO.readAllPersons(bestPerson.getAssociatedUsername());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertEquals(allPeople.get(0), compareTest);
        assertEquals(allPeople.get(1), second);
    }

    @Test
    public void deletePass() throws Exception
    {
        PersonDAO pDAO = null;
        try
        {
            Connection conn = db.openConnection();
            pDAO = new PersonDAO(conn);
            pDAO.createPerson(bestPerson);
            pDAO.dropPersonTable();
        }
        catch (DataAccessException e)
        {
            db.closeConnection(false);
        }
        assertNull(pDAO.readPerson(bestPerson.getPersonID()));
        db.closeConnection(true);
    }
}