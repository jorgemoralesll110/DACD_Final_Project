package ulpgc.dacd.jorgemorales;

import ulpgc.dacd.jorgemorales.model.Location;
import ulpgc.dacd.jorgemorales.model.Weather;

import java.io.*;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherController {

    private final OpenWeatherMapProvider weatherProvider;
    private final ActiveMQSender activeMQSender;

    public WeatherController() {
        this.weatherProvider = new OpenWeatherMapProvider();
        this.activeMQSender = new ActiveMQSender();
        scheduleTask();
    }

    public void execute() {
        List<Location> locations = locationsFromFile("locations.csv");

        for (Location location : locations) {
            try {
                Instant timestamp = Instant.now();
                String APIResponse = weatherProvider.getWeather(location, timestamp);
                List<Weather> weatherList = weatherProvider.parseWeatherData(APIResponse);
                sendWeatherData(location, timestamp, weatherList);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<Location> locationsFromFile(String filename) {
        List<Location> locations = new ArrayList<>();

        ClassLoader classLoader = WeatherController.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename);

        if (inputStream == null) {
            System.err.println("Error: File not Found: " + filename);
            return locations;
        }

        try (BufferedReader file = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = file.readLine()) != null) {
                String[] data = line.split(",");
                String island = data[0];
                double latitude = Double.parseDouble(data[1]);
                double longitude = Double.parseDouble(data[2]);

                Location location = new Location(island, latitude, longitude);
                locations.add(location);
            }
        } catch (Exception e) {
            System.err.println("Error reading the File: " + filename);
            e.printStackTrace();
        }
        return locations;
    }

    private void sendWeatherData(Location location, Instant timestamp, List<Weather> weatherList) {
        for (Weather weather : weatherList) {
            try {
                activeMQSender.storeWeatherData(weather, location, timestamp.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void scheduleTask() {
        Timer timer = new Timer();

        long delay = 0;
        long period = 6 * 3600 * 1000;

        TimerTask task = new TimerTask() {
            public void run() {
                execute();
            }
        };
        timer.scheduleAtFixedRate(task, delay, period);
    }
}
