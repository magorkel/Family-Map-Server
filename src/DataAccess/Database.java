package DataAccess;

import java.sql.*;

public class Database
{
    private Connection conn;
    public Connection openConnection() throws DataAccessException
    {
        try
        {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyMapDatabase.db";
            conn = DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException ("Unable to open connection to database");
        }
        return conn;
    }
    public Connection getConnection() throws DataAccessException
    {
        if (conn == null)
        {
            return openConnection();
        }
        else
        {
            return conn;
        }
    }
    public void closeConnection(boolean commit) throws DataAccessException
    {
        try
        {
            if (commit) { conn.commit(); }
            else { conn.rollback(); }
            conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }
    public void clearTables() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement())
        {
            ResultSet rs = conn.getMetaData().getTables(null, null, null, null);
            while(rs.next())
            {
                System.out.println(rs.getString("TABLE_NAME"));
            }
            String sql1 = "DELETE FROM User";
            stmt.executeUpdate(sql1);
            String sql2 = "DELETE FROM Token";
            stmt.executeUpdate(sql2);
            String sql3 = "DELETE FROM Person";
            stmt.executeUpdate(sql3);
            String sql = "DELETE FROM Event";
            stmt.executeUpdate(sql);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
