package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Response1.ClearResponse;

/**
 * check to make sure that the information being sent is actually usable
 * and deals with it appropriately
 */
public class ClearService
{
    /**
     * when called deletes all information in the whole database
     * calls delete in all DAO's
     * @return returns the clear response
     */
    public ClearResponse clearService ()
    {
        Database db = new Database();
        ClearResponse cr;
        try
        {
            db.openConnection();
            db.clearTables();
            cr = new ClearResponse("Clear succeeded.", true);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            cr = new ClearResponse("Internal server error.", false);
        }
        return cr;
    }
}
