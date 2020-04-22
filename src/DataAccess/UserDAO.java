package DataAccess;

import Model1.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * makes changes to the database based on the information sent
 */
public class UserDAO
{
    private final Connection conn;
    public UserDAO(Connection conn) {this.conn = conn;}

    /**
     * create
     * creates a new User and inserts them into the User table
     * @param user the user to be added
     * @throws DataAccessException
     */
    public void createUser(User user) throws DataAccessException
    {
        String sql = "INSERT INTO User (UserName, Password, Email, FirstName, LastName, Gender, PersonID) VALUES(?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * read
     * reads just a single row from the User table
     * @param userName specifies which row
     * @return returns just the row we want
     * @throws DataAccessException
     */
    public User readUser(String userName) throws DataAccessException
    {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE UserName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                user = new User(rs.getString("UserName"), rs.getString("Password"), rs.getString("Email"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"), rs.getString("PersonID"));
                return user;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public boolean found(String userName) throws DataAccessException
    {
        User user;
        boolean yes = false;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE UserName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                //user = new User(rs.getString("UserName"), rs.getString("Password"), rs.getString("Email"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"), rs.getString("PersonID"));
                yes = true;
                return yes;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return yes;
    }

    /**
     * delete
     * deletes the a single row
     * @param personID specifies which row
     * @throws DataAccessException
     */
    public void deleteUser(String personID) throws DataAccessException
    {
        User user;
        String sql = "DELETE FROM User WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, personID);
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing user");
        }
    }

    /**
     * drops the whole user table
     * @throws DataAccessException
     */
    public void dropUserTable() throws DataAccessException
    {
        User user;
        String sql = "DELETE FROM User";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException ("Error encountered while removing all users");
        }
    }
}
