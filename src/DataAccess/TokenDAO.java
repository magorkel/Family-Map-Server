package DataAccess;

import Model1.Token;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * makes changes to the database based on the information sent
 */
public class TokenDAO
{
    private final Connection conn;
    public TokenDAO(Connection conn) {this.conn = conn;}
    /**
     * create
     * creates a token and inserts it into the Token table
     * @param token the token to be added
     * @throws DataAccessException exception
     */
    public void createToken(Token token) throws DataAccessException
    {
        String sql = "INSERT INTO Token(UserName, Password, UniqueToken) VALUES(?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, token.getUserName());
            stmt.setString(2, token.getPassword());
            stmt.setString(3, token.getUniqueToken());

            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
    /**
     * read
     * reads just a single row from the token table
     * @param uniqueToken specifies which row
     * @return returns just the row we want
     * @throws DataAccessException exception
     */
    public Token readToken(String uniqueToken) throws DataAccessException
    {
        Token token;
        ResultSet rs = null;
        String sql = "SELECT * FROM Token WHERE UniqueToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, uniqueToken);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                token = new Token(rs.getString("UserName"), rs.getString("Password"), rs.getString("UniqueToken"));
                return token;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding token");
        }
        finally
        {
            if (rs != null)
            {
                try {rs.close();}
                catch (SQLException e) {e.printStackTrace();}
            }
        }
        return null;
    }
    /**
     * delete
     * deletes the single row specified
     * @param personID specifies which row
     * @throws DataAccessException exception
     */
    public void deleteToken(String personID) throws DataAccessException
    {
        String sql = "DELETE FROM Token WHERE PersonID = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, personID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing token");
        }
    }
    public void deleteTokenFromUsername(String userName) throws DataAccessException
    {
        String sql = "DELETE FROM Token WHERE UserName = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, userName);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing token");
        }
    }
    /**
     * drops the whole Token table
     * @throws DataAccessException exception
     */
    public void dropTokenTable() throws DataAccessException
    {
        String sql = "DELETE FROM Token";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing all tokens");
        }
    }
}
