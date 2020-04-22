package Response1;

import Model1.Person;

import java.util.ArrayList;

/**
 * returns a message of whether the family members in the persons tree were able to be found
 */
public class PersonResponse extends Response
{
    ArrayList<Person> data;

    public PersonResponse(ArrayList<Person> data, String message, Boolean success)
    {
        super(message, success);
        this.data = data;
    }

    public ArrayList<Person> getData()
    {
        return data;
    }

    public void setData(ArrayList<Person> data)
    {
        this.data = data;
    }
}
