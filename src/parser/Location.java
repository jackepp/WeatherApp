package parser;

/**
 * @author Jacob Permansson
 *
 * This is just the location calss, where the properties of the locations objects are stored, and can be reached from its methods.
 */
public class Location {

    private String name;
    private String altitude;
    private String latitude;
    private String longitude;

    public Location(String name, String altitude, String latitude, String longitude){
        this.name = name;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName(){
        return name;
    }

    public String getAltitude(){
        return altitude;
    }

    public String getLatitude(){
        return latitude;
    }
    public String getLongitude(){
        return longitude;
    }


}
