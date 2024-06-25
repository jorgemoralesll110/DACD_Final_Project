package ulpgc.dacd.jorgemorales;

import ulpgc.dacd.jorgemorales.model.Location;
import ulpgc.dacd.jorgemorales.model.Weather;

import java.time.Instant;
import java.util.List;

public interface WeatherProvider {
    String getWeather(Location location, Instant timestamp) throws Exception;

    void storeWeatherData(Weather weather);
}
