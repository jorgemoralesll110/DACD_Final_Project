package ulpgc.dacd.jorgemorales;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.time.LocalDate;
import java.time.ZoneId;

public class FileEventStore implements EventStore{
    private static final String datalakePath = "datalake/eventStore";

    @Override
    public void saveEvent(String eventJson) throws IOException {
        System.out.println("Saving event...");
        Gson gson = new Gson();
        Map<String, Object> event = gson.fromJson(eventJson, Map.class);

        String timestamp = (String) event.get("ts");
        String source = (String) event.get("ss");
        String topicName = "prediction.Weather";

        Instant ts = Instant.parse(timestamp);
        LocalDate eventDate = ts.atZone(ZoneId.of("UTC")).toLocalDate();
        String datePath = eventDate.format(DateTimeFormatter.BASIC_ISO_DATE);

        String directoryPath = datalakePath + "/" + topicName + "/" + datePath + "/" + source;
        Files.createDirectories(Paths.get(directoryPath));
        System.out.println("Directory created: " + directoryPath);

        String filePath = String.format("%s/%s.events", directoryPath, datePath);
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.write(eventJson + "\n");
            System.out.println("Event saved: " + filePath);
            System.out.println("Event: " + eventJson);
        }



    }
}
