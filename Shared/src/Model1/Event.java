package Model1;

import java.util.Objects;

/**
 * the Event object
 * constructor and getters and setters
 */
public class Event
{
    String eventID;
    String associatedUsername;
    String personID;
    Double latitude;
    Double longitude;
    String country;
    String city;
    String eventType;
    int year;

    public Event(String eventID, String associatedUsername, String personID, Double latitude, Double longitude, String country, String city, String eventType, int year)
    {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID()
    {
        return eventID;
    }

    public void setEventID(String eventID)
    {
        this.eventID = eventID;
    }

    public String getAssociatedUsername()
    {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername)
    {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return year == event.year &&
                eventID.equals(event.eventID) &&
                associatedUsername.equals(event.associatedUsername) &&
                personID.equals(event.personID) &&
                latitude.equals(event.latitude) &&
                longitude.equals(event.longitude) &&
                country.equals(event.country) &&
                city.equals(event.city) &&
                eventType.equals(event.eventType);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);
    }
}
