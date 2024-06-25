package ulpgc.dacd.jorgemorales;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.google.gson.Gson;
import ulpgc.dacd.jorgemorales.model.Booking;
import ulpgc.dacd.jorgemorales.model.Hotel;

import java.util.List;


public class ActiveMQHotelSubscriber implements MessageListener {
    public static final String brokerURL = "tcp://localhost:61616";
    public static final String topicName = "hotel.Avalability";
    public static final String clientID = "hotelProviderClient";
    private final HotelProvider hotelProvider;

    public ActiveMQHotelSubscriber(HotelProvider hotelProvider) {
        this.hotelProvider = hotelProvider;
    }

    public void start() throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(clientID);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createDurableSubscriber(topic, clientID);
        consumer.setMessageListener(this);
        System.out.println("Hotel subscriber started to topic " + topicName);
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String eventJson = ((TextMessage) message).getText();
                Gson gson = new Gson();
                Booking booking = gson.fromJson(eventJson, Booking.class);
                String island = "Island_name_from_message";
                List<Hotel> hotels = hotelProvider.searchHotel(island, booking);
                for (Hotel hotel : hotels) {
                    System.out.println("Hotel: " + hotel.getName() + " on " + hotel.getIsland() + " costs " + hotel.getPrice());
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
