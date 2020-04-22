package Request1;


import Model1.Event;
import Model1.Person;
import Model1.User;

/**
 * holds the User, Person and Event tables
 * getters and setters to populate these tables
 */
public class LoadRequest
{
    User[] users;
    Person[] persons;
    Event[] events;

    public LoadRequest(User[] users, Person[] persons, Event[] events)
    {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers()
    {
        return users;
    }

    public void setUsers(User[] users)
    {
        this.users = users;
    }

    public Person[] getPersons()
    {
        return persons;
    }

    public void setPersons(Person[] persons)
    {
        this.persons = persons;
    }

    public Event[] getEvents()
    {
        return events;
    }

    public void setEvents(Event[] events)
    {
        this.events = events;
    }
}
