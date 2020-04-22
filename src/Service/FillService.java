package Service;

import DataAccess.*;
import Model1.*;
import Response1.FillResponse;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * check to make sure that the information being sent is actually usable
 * and deals with it appropriately
 */
public class FillService
{
    private ArrayList<Person> persons = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();

    /**
     * fills the database for the UserName that we are on with the number of generations requested
     * deletes information already stored in DB associated with the UserName
     * @param userName the person we are on now, or the person logged in
     * @param numberOfGenerations optional, how far back we want to populate
     * @return specifies what happened, if the information was added/changed or if something went wrong
     */
    public FillResponse fillService(String userName, int numberOfGenerations)
    {
        Database db = new Database();
        FillResponse fr;
        try
        {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            EventDAO eDAO = new EventDAO(conn);
            pDAO.deletePersonFromUser(userName);
            eDAO.deleteEventFromUser(userName);

            UserDAO uDAO = new UserDAO(conn);
            User user = uDAO.readUser(userName);

            if (user == null)
            {
                fr = new FillResponse("Invalid userName, does not exist", false);
                db.closeConnection(false);
                return fr;
            }
            String associatedUsername = user.getUserName();

            Person starterPerson = new Person(user.getPersonID(), associatedUsername, user.getFirstName(), user.getLastName(), user.getGender(), null, null, null);

            //birth
            Locations locations = Locations.readFile();
            Location birthLocation = locations.getLocation();
            Random num = new Random();
            int year = num.nextInt(2020);
            if (year <= 1950) { year += 1950; }
            if (year > 2020) { year = 2020; }
            Event birthEvent = new Event(UUID.randomUUID().toString(), userName, user.getPersonID(), birthLocation.getLatitude(), birthLocation.getLongitude(), birthLocation.getCountry(), birthLocation.getCity(), "Birth", year);
            events.add(birthEvent);

            fillGenerations(starterPerson, numberOfGenerations, userName, year);

            for (Person person : persons)
            {
                pDAO.createPerson(person);
            }
            for (Event event : events)
            {
                eDAO.createEvent(event);
            }

            fr = new FillResponse("Successfully added " + persons.size() + " persons and " + events.size() + " events to the database.", true);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            fr = new FillResponse("Invalid username or generations parameter.", false);
        }
        catch (FileNotFoundException f)
        {
            fr = new FillResponse("Internal server error.", false);
        }
        return fr;
    }

    private void fillGenerations(Person current, int numberOfGenerations, String associatedUsername, int currentBirthYear) throws FileNotFoundException
    {
        if(numberOfGenerations <= 0)
        {
            persons.add(current);
            return;
        }

        Names name = new Names();
        name.readNames();
        String momName = name.getMomName();
        String dadName = name.getDadName();
        String lastName = name.getLastName();

        String momID = momName + "_" + lastName;
        String dadID = dadName + "_" + lastName;

        //locations
        Locations locations = Locations.readFile();
        Location momBirthLocation = locations.getLocation();
        Location dadBirthLocation = locations.getLocation();
        Location marriageLocation = locations.getLocation();
        Location momDeathLocation = locations.getLocation();
        Location dadDeathLocation = locations.getLocation();

        //dates
        Random num = new Random();
        int momBirthYear = currentBirthYear - num.nextInt(50);
        if (momBirthYear >= (currentBirthYear - 18)) {momBirthYear -= 18;}
        else { }
        momBirthYear = currentBirthYear - 18 > momBirthYear && currentBirthYear - 13 < momBirthYear ?
                momBirthYear : currentBirthYear - 18;
        int dadBirthYear = momBirthYear - num.nextInt(5);
        int marriageYear = momBirthYear + num.nextInt(15) + 15;
        //if (marriageYear <= (momBirthYear + 14)) {marriageYear += 14;}
        int momDeathYear = momBirthYear + num.nextInt(120);
        if(momDeathYear < currentBirthYear) { momDeathYear = currentBirthYear;}
        int dadDeathYear = dadBirthYear + num.nextInt(120);
        if (dadDeathYear < currentBirthYear) { dadDeathYear = currentBirthYear;}

        Person mom = new Person(momID, associatedUsername, momName, lastName, "f", null, null, dadID);
        Event momBirth = new Event(UUID.randomUUID().toString(), associatedUsername, momID, momBirthLocation.getLatitude(), momBirthLocation.getLongitude(), momBirthLocation.getCountry(), momBirthLocation.getCity(), "Birth", momBirthYear);
        Event momMarriage = new Event (UUID.randomUUID().toString(), associatedUsername, momID, marriageLocation.getLatitude(), marriageLocation.getLongitude(), marriageLocation.getCountry(), marriageLocation.getCity(), "Marriage", marriageYear);
        Event momDeath = new Event(UUID.randomUUID().toString(),associatedUsername, momID, momDeathLocation.getLatitude(), momDeathLocation.getLongitude(), momDeathLocation.getCountry(), momDeathLocation.getCity(), "Death", momDeathYear);

        Person dad = new Person(dadID, associatedUsername, dadName, lastName, "m", null, null, momID);
        Event dadBirth = new Event(UUID.randomUUID().toString(), associatedUsername, dadID, dadBirthLocation.getLatitude(), dadBirthLocation.getLongitude(), dadBirthLocation.getCountry(), dadBirthLocation.getCity(), "Birth", dadBirthYear);
        Event dadMarriage = new Event (UUID.randomUUID().toString(), associatedUsername, dadID, marriageLocation.getLatitude(), marriageLocation.getLongitude(), marriageLocation.getCountry(), marriageLocation.getCity(), "Marriage", marriageYear);
        Event dadDeath = new Event(UUID.randomUUID().toString(), associatedUsername, dadID, dadDeathLocation.getLatitude(), dadDeathLocation.getLongitude(), dadDeathLocation.getCountry(), dadDeathLocation.getCity(), "Death", dadDeathYear);

        events.add(momBirth);
        events.add(momMarriage);
        events.add(momDeath);
        events.add(dadBirth);
        events.add(dadMarriage);
        events.add(dadDeath);

        current.setMotherID(momID);
        current.setFatherID(dadID);
        persons.add(current);

        fillGenerations(mom, numberOfGenerations - 1, associatedUsername, momBirthYear);
        fillGenerations(dad, numberOfGenerations - 1, associatedUsername, dadBirthYear);
    }
}
