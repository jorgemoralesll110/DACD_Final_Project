package ulpgc.dacd.jorgemorales.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ulpgc.dacd.jorgemorales.InstantAdapter;
import ulpgc.dacd.jorgemorales.model.Location;
import ulpgc.dacd.jorgemorales.model.Weather;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class EventBuilder {
    public static String buildEvent(Weather weather, Location location, String timestamp) {
        Map<String, Object> event = new HashMap<>();
        event.put("ts", timestamp);
        event.put("ss", "prediction-provider");
        event.put("predictionTs", Instant.now().toString());
        event.put("location", location);
        event.put("temperature", weather.getTemperature());
        event.put("humidity", weather.getHumidity());
        event.put("windSpeed", weather.getWind());
        event.put("rain", weather.getRain());

        Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
        return gson.toJson(event);
    }
}