package Test;

import DataAccess.Database;
import Model1.Event;
import Model1.Person;
import Model1.User;
import Request1.LoadRequest;
import Response1.LoadResponse;
import Service.LoadService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest
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
    public void loadTestPass() throws Exception
    {
        Person person1;
        Person person2;
        User user;
        Event event;

        LoadService loadService = new LoadService();
        LoadResponse loadResponse = new LoadResponse("beginning empty", false);
        try
        {
            person1 = new Person("hello", "harleymc", "Harley", "Christensen", "f", null, null, null);
            person2 = new Person("sup", "harleymc", "Jared", "Reed", "m", null, null, null);
            user = new User("harleymc", "12345", "h@gmail.com", "Harley", "Christensen", "f", "hello");
            event = new Event("12", "harleymc", "hello", 23.3, 12.2, "USA", "Springville", "marriage", 2020);

            Person[] personList = { person1, person2 };
            User[] userList = { user };
            Event[] eventList = { event };

            LoadRequest loadRequest = new LoadRequest(userList, personList, eventList);
            loadResponse = loadService.loadService(loadRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertTrue(loadResponse.getSuccess());
        assertEquals(loadResponse.getMessage(), "Successfully added 1 users, 2 persons, and 1 events to the database.");
    }

    @Test
    public void loadTestFail() throws Exception
    {
        //???not sure if i can do a fail for this one?
    }
}
