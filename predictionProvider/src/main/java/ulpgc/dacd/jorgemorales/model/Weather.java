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

    public Weather(double temperature, double humidity, double precipitation, double windSpeed, double clouds, Location locationApi, String dataTimeString) {
    }

    public Instant getTs() {
        return ts;
    }

    public void setTs(Instant ts) {
        this.ts = ts;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public Instant getPredictionTime() {
        return predictionTime;
    }

    public void setPredictionTime(Instant predictionTime) {
        this.predictionTime = predictionTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getClouds() {
        return clouds;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}