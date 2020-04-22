package DataAccess;

import Model1.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * makes changes to the database based on the information sent
 */
public class PersonDAO
{
    private final Connection conn;
    public PersonDAO(Connection conn) {this.conn = conn;}
    /**
     * create
     * creates a person and inserts them into the Person table
     * @param person the person to be added
     * @throws DataAccessException
     */
    public void createPerson(Person person) throws DataAccessException
    {
        String sql = "INSERT INTO Person (PersonID, AssociatedUsername, FirstName, LastName, Gender, FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
    /**
     * read
     * reads just a single row from the Person table
     * @param personID specifies which row
     * @return returns just the row we want
     * @throws DataAccessException
     */
    public Person readPerson(String personID) throws DataAccessException
    {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE PersonID = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"), rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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
    public Person readPersonWithUsername(String userName) throws DataAccessException
    {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE AssociatedUsername = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"), rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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
     * readAll
     * reads all the people stored in the Person table associated with that userName
     * @param AssociatedUsername the username given to find everyone else
     * @return returns the whole table
     * @throws DataAccessException
     */
    public ArrayList<Person> readAllPersons(String AssociatedUsername)  throws DataAccessException
    {
        ArrayList<Person> allPersons = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt =  conn.prepareStatement(sql))
        {
            stmt.setString(1, AssociatedUsername);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                allPersons.add(new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"), rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID")));
            }
            return allPersons;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        }
        finally
        {
            if (rs != null)
            {
                try {rs.close();}
                catch (SQLException e) {e.printStackTrace();}
            }
        }
    }
    /**
     * delete
     * deletes the a single row from the Person table
     * @param personID specifies which row
     * @throws DataAccessException
     */
    public void deletePerson(String personID) throws DataAccessException
    {
        String sql = "DELETE FROM Person WHERE PersonID = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, personID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing person");
        }
    }
    public void deletePersonFromUser(String userName) throws DataAccessException
    {
        String sql = "DELETE FROM Person WHERE AssociatedUsername = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, userName);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing person");
        }
    }
    /**
     * drops the whole person table
     * @throws DataAccessException
     */
    public void dropPersonTable() throws DataAccessException
    {
        String sql = "DELETE FROM Person";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing all persons");
        }
    }
}
