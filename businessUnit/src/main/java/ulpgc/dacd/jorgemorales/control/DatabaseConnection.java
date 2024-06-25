package ulpgc.dacd.jorgemorales.control;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:Hotel_Weather.db:" + Paths.get("src/main/resources/database/hotel_weather_db").toAbsolutePath().toString();
    private Connection connection;

    public DatabaseConnection() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        connection.close();
    }

    private void createTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createWeatherTable = "CREATE TABLE IF NOT EXISTS weather (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "timestamp TIMESTAMP NOT NULL, " +
                    "source VARCHAR(255) NOT NULL, " +
                    "temperature DOUBLE NOT NULL, " +
                    "wind_speed DOUBLE NOT NULL, " +
                    "rain_probability DOUBLE NOT NULL, " +
                    "humidity DOUBLE NOT NULL)";
            statement.execute(createWeatherTable);

        
            String createBookingsTable = "CREATE TABLE IF NOT EXISTS bookings (" +
                "id IDENTITY PRIMARY KEY, " +
                "hotel_key VARCHAR(255) NOT NULL, " +
                "check_in_date DATE NOT NULL, " +
                "check_out_date DATE NOT NULL)";
            statement.execute(createBookingsTable);
        }
    }
}
