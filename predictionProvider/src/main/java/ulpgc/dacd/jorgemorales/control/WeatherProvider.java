package ulpgc.dacd.jorgemorales.control;

import ulpgc.dacd.jorgemorales.model.Location;
import ulpgc.dacd.jorgemorales.model.Weather;

import java.time.Instant;

public interface WeatherProvider {
    String getWeather(Location location, Instant timestamp) throws Exception;

    void storeWeatherData(Weather weather);
}
