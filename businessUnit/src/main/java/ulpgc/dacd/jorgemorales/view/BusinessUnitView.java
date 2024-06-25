package ulpgc.dacd.jorgemorales.view;

import ulpgc.dacd.jorgemorales.control.DataMart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BusinessUnitView {
    private final DataMart dataMart;
    private Connection connection;

    public BusinessUnitView(DataMart dataMart, Connection connection) {
        this.dataMart = dataMart;
        this.connection = connection;
    }

    public void displayWeatherData() throws SQLException {
        System.out.println("Weather Data:");
        String query = "SELECT * FROM weather";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Timestamp: " + rs.getString("timestamp") +
                        ", Source: " + rs.getString("source") +
                        ", Temperature: " + rs.getDouble("temperature") +
                        ", Wind Speed: " + rs.getDouble("wind_speed") +
                        ", Rain Probability: " + rs.getDouble("rain_probability") +
                        ", Humidity: " + rs.getDouble("humidity"));
            }
        }
    }

    public void displayBookingData() throws SQLException {
        System.out.println("Booking Data:");
        String query = "SELECT * FROM bookings";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Hotel Key: " + rs.getString("hotel_key") +
                        ", Check-In Date: " + rs.getString("check_in_date") +
                        ", Check-Out Date: " + rs.getString("check_out_date"));
            }
        }
    }
}
