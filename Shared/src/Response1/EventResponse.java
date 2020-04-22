package Response1;

import Model1.Event;

import java.util.ArrayList;

/**
 * provides the response of whether you were able to retrieve all the events for a specific user/person
 */
public class EventResponse extends Response
{
    ArrayList<Event> data;

    public EventResponse(ArrayList<Event> data, String message, Boolean success)
    {
        super(message, success);
        this.data = data;
    }

    public ArrayList<Event> getData()
    {
        return data;
    }

    public void setData(ArrayList<Event> data)
    {
        this.data = data;
    }
}
