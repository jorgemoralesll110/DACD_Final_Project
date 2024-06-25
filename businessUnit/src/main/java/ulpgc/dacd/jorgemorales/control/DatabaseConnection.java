package ulpgc.dacd.jorgemorales.control;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:" + Paths.get("src/main/resources/database/business_unit_db.db").toAbsolutePath();
    private Connection connection;

    public DatabaseConnection() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        createTables();
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createWeatherTable = "CREATE TABLE IF NOT EXISTS weather (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "timestamp TEXT NOT NULL, " +
                    "source TEXT NOT NULL, " +
                    "temperature REAL NOT NULL, " +
                    "wind_speed REAL NOT NULL, " +
                    "rain_probability REAL NOT NULL, " +
                    "humidity REAL NOT NULL)";
            statement.execute(createWeatherTable);

            String createBookingsTable = "CREATE TABLE IF NOT EXISTS bookings (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "hotel_key TEXT NOT NULL, " +
                    "check_in_date TEXT NOT NULL, " +
                    "check_out_date TEXT NOT NULL)";
            statement.execute(createBookingsTable);
        }
    }
}
