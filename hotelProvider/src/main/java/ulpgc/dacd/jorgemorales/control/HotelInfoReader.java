package ulpgc.dacd.jorgemorales.control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.*;

public class HotelInfoReader {
    private final Map<String, List<String>> islandHotelMap = new HashMap<>();

    public HotelInfoReader(String filePath) throws Exception {
        loadHotelInfo(filePath);
    }

    private void loadHotelInfo(String filePath) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream("hotelAPIKey.csv"))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String island = parts[0];
                String hotel = parts[1];
                islandHotelMap.computeIfAbsent(island, k -> new ArrayList<>()).add(hotel);
            }
        }
    }

    public List<String> getHotelKeyForIsland(String island) {
        return islandHotelMap.getOrDefault(island, new ArrayList<>());
    }

}
