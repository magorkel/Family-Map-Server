package Response1;

/**
 * Tells whether the required people and events were added to the database
 */
public class FillResponse extends Response
{
    public FillResponse(String message, Boolean success)
    {
        super(message, success);
    }
}
