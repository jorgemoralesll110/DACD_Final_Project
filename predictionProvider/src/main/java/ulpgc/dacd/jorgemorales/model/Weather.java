package ulpgc.dacd.jorgemorales.model;

import java.time.Instant;

public class Weather {
    private Instant ts;
    private String ss;
    private Instant predictionTime;
    private Location location;
    private double temperature;
    private double humidity;
    private double rain;
    private double wind;
    private double clouds;
    private String timestamp;

    public Weather(Instant ts, String ss, Instant predictionTime, Location location, double temperature, double humidity, double rain, double wind, double clouds, String timestamp) {
        this.ts = ts;
        this.ss = ss;
        this.predictionTime = predictionTime;
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rain = rain;
        this.wind = wind;
        this.clouds = clouds;
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }



    public double getRain() {
        return rain;
    }

    public double getWind() {
        return wind;
    }


}