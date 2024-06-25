package ulpgc.dacd.jorgemorales;

import ulpgc.dacd.jorgemorales.model.Location;
import ulpgc.dacd.jorgemorales.model.Weather;

import java.time.Instant;

public interface WeatherStore {
    void saveWeather(Weather weather,Location location, Instant timestamp) throws Exception;

    void storeWeatherData(Weather weather, Location location, String timestamp);
}
