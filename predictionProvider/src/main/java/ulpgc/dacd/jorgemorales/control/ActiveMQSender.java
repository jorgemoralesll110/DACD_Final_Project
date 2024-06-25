package ulpgc.dacd.jorgemorales.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import ulpgc.dacd.jorgemorales.model.Location;
import ulpgc.dacd.jorgemorales.model.Weather;

import java.time.Instant;

public class ActiveMQSender implements WeatherStore {
    private static final String brokerURL = "tcp://localhost:61616";
    private static final String topicName = "prediction.Weather";
    private Connection connection;
    private Session session;

    private void setConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    private void publishMessages(Weather weather) throws JMSException {
        Destination destination = session.createTopic(topicName);
        MessageProducer producer = session.createProducer(destination);
        Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
        String weatherJson = gson.toJson(weather);
        TextMessage message = session.createTextMessage(weatherJson);

        System.out.println("Generated Event: " + weatherJson);

        producer.send(message);
    }

    private void closeConnection() throws JMSException {
        session.close();
        connection.close();
    }

    @Override
    public void saveWeather(Weather weather, Location location, Instant timestamp) {
        try {
            setConnection();
            publishMessages(weather);
            closeConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeWeatherData(Weather weather, Location location, String timestamp) {
        try {
            setConnection();
            publishMessages(weather);
            closeConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
