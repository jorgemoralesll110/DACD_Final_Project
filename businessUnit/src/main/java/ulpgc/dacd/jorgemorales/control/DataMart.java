package ulpgc.dacd.jorgemorales.control;

import ulpgc.dacd.jorgemorales.model.Booking;
import ulpgc.dacd.jorgemorales.model.Weather;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataMart {
    private Connection connection;

    public DataMart(Connection connection) {
        this.connection = connection;
    }

    public void addWeatherData(Weather weather) throws SQLException {
        String insertSQL = "INSERT INTO weather (timestamp, source, temperature, wind_speed, rain_probability, humidity) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, weather.getTimestamp());
            statement.setString(2, weather.getSs());
            statement.setDouble(3, round(weather.getTemperature() - 273.15));
            statement.setDouble(4, weather.getWind());
            statement.setDouble(5, weather.getRain());
            statement.setDouble(6, weather.getHumidity());

            statement.executeUpdate();
        }
    }

    public void addBookingData(Booking booking) throws SQLException {
        String insertSQL = "INSERT INTO bookings (hotel_key, check_in_date, check_out_date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, booking.getCheckInDate());
            statement.setString(2, booking.getCheckOutDate());

            statement.executeUpdate();
        }
    }

    private double round(double value) {
        BigDecimal number = new BigDecimal(value);
        number = number.setScale(2, RoundingMode.HALF_UP);
        return number.doubleValue();
    }
}
