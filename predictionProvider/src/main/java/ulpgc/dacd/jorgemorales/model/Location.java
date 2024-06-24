package ulpgc.dacd.jorgemorales.model;

import java.time.Instant;

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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIsland() {
        return island;
    }

    public void setIsland(String island) {
        this.island = island;
    }
}