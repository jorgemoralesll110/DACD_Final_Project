package ulpgc.dacd.jorgemorales.control;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class FileEventStore implements EventStore {

    @Override
    public void saveEvent(Map<String, Object> event) throws IOException {
        String timestamp = (String) event.get("ts");
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp 'ts' cannot be null");
        }

        try {
            Instant.parse(timestamp);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid timestamp format: " + timestamp, e);
        }

        String source = (String) event.get("ss");
        String predictionTs = (String) event.get("predictionTs");


        String date = predictionTs.substring(0, 10).replace("-", "");
        String directoryPath = String.format("datalake/eventstore/%s/%s", "prediction.Weather", source);
        String fileName = String.format("%s/%s.events", directoryPath, date);

        // Create directories if they do not exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean dirsCreated = directory.mkdirs();
            if (dirsCreated) {
                System.out.println("Directories created: " + directoryPath);
            } else {
                System.err.println("Failed to create directories: " + directoryPath);
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(event.toString());
            writer.newLine();
        }

        System.out.println("Event stored in file: " + fileName);
    }
}