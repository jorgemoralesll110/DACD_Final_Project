package ulpgc.dacd.jorgemorales;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class HotelInfoReader {

    private final Map<String, List<String>> islandHotelMap = new HashMap<>();

    public HotelInfoReader() throws Exception {
        loadHotelInfo();
    }

    private void loadHotelInfo() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream("hotel_info.csv"))))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String island = parts[0];
                String hotelKey = parts[1];
                islandHotelMap.computeIfAbsent(island, k -> new ArrayList<>()).add(hotelKey);
            }
        }
    }

    public List<String> getHotelKeysForIsland(String island) {
        return islandHotelMap.getOrDefault(island, new ArrayList<>());
    }
}
