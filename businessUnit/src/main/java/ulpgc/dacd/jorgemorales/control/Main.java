package ulpgc.dacd.jorgemorales.control;

import jakarta.jms.JMSException;
import ulpgc.dacd.jorgemorales.view.BusinessUnitView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            DataMart dataMart = new DataMart(connection);
            BrokerConnection brokerConnection = new BrokerConnection();
            EventConsumer weatherConsumer = new EventConsumer(dataMart, brokerConnection.getSession(), "prediction.Weather");
            EventConsumer bookingConsumer = new EventConsumer(dataMart, brokerConnection.getSession(), "booking.Hotel");

            BusinessUnitView view = new BusinessUnitView(dataMart, connection);
            view.displayWeatherData();
            view.displayBookingData();

        } catch (SQLException | JMSException e) {
            e.printStackTrace();
        }
    }
}
