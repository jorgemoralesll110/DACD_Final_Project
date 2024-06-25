package ulpgc.dacd.jorgemorales.control;

import com.google.gson.Gson;
import jakarta.jms.*;
import jdk.jfr.Event;
import org.apache.activemq.ActiveMQConnectionFactory;
import ulpgc.dacd.jorgemorales.model.Booking;
import ulpgc.dacd.jorgemorales.model.Hotel;

import java.util.List;

public static class ActiveMQHotelSubscriber implements MessageListener {

    private static final String brokerURL = "tcp://localhost:61616";
    private static final String topicName = "hotel.Availability";
    private static final String clientID = "HotelProviderClient";
    private final HotelProvider hotelProvider;

    public ActiveMQHotelSubscriber(HotelProvider hotelProvider) {
        this.hotelProvider = hotelProvider;
    }

    public void start() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(clientID);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createDurableSubscriber(topic, clientID);
        consumer.setMessageListener(this);
        System.out.println("Subscribed to " + topicName + " topic");
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String eventJson = ((TextMessage) message).getText();
                Gson gson = new Gson();
                Booking event = gson.fromJson(eventJson, Booking.class);
                Booking booking = new Booking(event.getCheckInDate(), event.getCheckOutDate());
                List<Hotel> hotels = hotelProvider.searchHotels(event., booking);
                for (Hotel hotel : hotels) {
                    System.out.println("Hotel Name: " + hotel.getName());
                    System.out.println("Price: " + hotel.getPrice());
                    System.out.println();
                }
            }
            } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
        }
    }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
