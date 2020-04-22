package Test;

import DataAccess.Database;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import DataAccess.TokenDAO;
import Model1.Person;
import Model1.Token;
import Response1.PersonResponse;
import Service.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest
{
    private Database db;

    @BeforeEach
    public void setUp() throws Exception
    {
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();

        Token token = new Token("harleymc", "1234", "helloWorld");
        Person person1 = new Person("1", "harleymc", "Shauna", "Christensen", "f", null, null, "2");
        Person person2 = new Person("2", "harleymc", "Rich", "Christensen", "m", null, null, "1");
        Person person3 = new Person ("3", "jared", "Jared","Reed", "m", null, null, null);

        TokenDAO tokenDAO = new TokenDAO(conn);
        tokenDAO.createToken(token);
        PersonDAO personDAO = new PersonDAO(conn);
        personDAO.createPerson(person1);
        personDAO.createPerson(person2);
        personDAO.createPerson(person3);

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
    public void personTestPass() throws Exception
    {
        PersonService personService = new PersonService();
        PersonResponse personResponse = null;
        try
        {
            personResponse = personService.personService("helloWorld", null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertTrue(personResponse.getSuccess());
        assertNull(personResponse.getMessage());
    }

    @Test
    public void personTestFail() throws Exception
    {
        PersonService personService = new PersonService();
        PersonResponse personResponse = null;
        try
        {
            personResponse = personService.personService("helloWorld", "3");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertFalse(personResponse.getSuccess());
        assertNotNull(personResponse.getMessage());
    }
}
