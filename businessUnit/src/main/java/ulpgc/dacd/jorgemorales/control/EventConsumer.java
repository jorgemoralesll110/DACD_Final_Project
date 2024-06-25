package ulpgc.dacd.jorgemorales.control;

import jakarta.jms.*;
import ulpgc.dacd.jorgemorales.model.Booking;
import ulpgc.dacd.jorgemorales.model.Weather;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EventConsumer implements MessageListener {
    private DataMart dataMart;
    private Session session;
    private String topicName;

    public EventConsumer(DataMart dataMart, Session session, String topicName) throws JMSException {
        this.dataMart = dataMart;
        this.session = session;
        this.topicName = topicName;
        subscribeToTopic();
    }

    private void subscribeToTopic() throws JMSException {
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String eventJson = ((TextMessage) message).getText();
                Map<String, Object> event = parseEvent(eventJson);

                if (topicName.equals("prediction.Weather")) {
                    Weather weather = createWeatherEvent(event);
                    dataMart.addWeatherData(weather);
                    System.out.println("Weather event added: " + weather);
                } else if (topicName.equals("booking.Hotel")) {
                    Booking booking = createBookingEvent(event);
                    dataMart.addBookingData(booking);
                    System.out.println("Booking event added: " + booking);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, Object> parseEvent(String eventJson) {
        return new HashMap<>();
    }

    private Weather createWeatherEvent(Map<String, Object> event) {
        return new Weather(
                Instant.parse((String) event.get("ts")),
                (String) event.get("ss"),
                (Double) event.get("temperature"),
                (Double) event.get("windSpeed"),
                (Double) event.get("rainProbability"),
                (Double) event.get("humidity")
        );
    }

    private Booking createBookingEvent(Map<String, Object> event) {
        return new Booking(
                Instant.parse((String) event.get("checkInDate")),
                Instant.parse((String) event.get("checkOutDate"))
        );
    }
}
