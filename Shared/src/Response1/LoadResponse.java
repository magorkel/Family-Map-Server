package Response1;

/**
 * provides a success message of whether the arrays were able to be populated
 */
public class LoadResponse extends Response
{
    public LoadResponse(String message, Boolean success)
    {
        super(message, success);
    }
}
