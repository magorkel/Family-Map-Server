package Response1;

/**
 * clears data and returns message of whether it was successful or not
 */
public class ClearResponse extends Response
{
    public ClearResponse(String message, Boolean success)
    {
        super(message, success);
    }
}
