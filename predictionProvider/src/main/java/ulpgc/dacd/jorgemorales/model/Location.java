package ulpgc.dacd.jorgemorales.model;


public class Location {
    private double latitude;
    private double longitude;
    private String island;

    public Location(String island, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.island = island;
    }

    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }

}