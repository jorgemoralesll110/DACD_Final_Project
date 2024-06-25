package ulpgc.dacd.jorgemorales.control;

import com.google.gson.Gson;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.HashMap;


public class DatalakeEventSubscriber implements EventSubscriber, MessageListener{
    private static final String brokerURL = "tcp://localhost:61616";
    static final String topicName = "prediction.Weather";
    private static final String clientID = "DataLakeEventSubscriberClient";
    private final EventStore eventStore;
    Connection connection;
    Session session;

    public DatalakeEventSubscriber(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void start() throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientID);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createDurableConsumer(topic, "datalake-event-store");
        consumer.setMessageListener(this);
        System.out.println("Subscribed to topic " + topicName);
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String eventJson = ((TextMessage) message).getText();
                Gson gson = new Gson();
                Map<String, Object> event = gson.fromJson(eventJson, HashMap.class);
                event.put("ts", Instant.now().toString());
                event.put("ss", "OpenWeatherMap");
                Map<String, Object> mappedEvent = mapEventFields(event);
                validateEvent(event);
                eventStore.saveEvent(mappedEvent);
                System.out.println("Event stored: " + mappedEvent);
            } catch (JMSException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, Object> mapEventFields(Map<String, Object> event) {
        Map<String, Object> mappedEvent = new HashMap<>();
        mappedEvent.put("ts", event.get("ts"));
        mappedEvent.put("ss", event.get("ss"));
        mappedEvent.put("predictionTs", event.get("predictionTs"));
        mappedEvent.put("latitude", event.get("latitude"));
        mappedEvent.put("longitude", event.get("longitude"));
        mappedEvent.put("temperature", event.get("temperature"));
        mappedEvent.put("windSpeed", event.get("wind"));
        mappedEvent.put("rainProbability", event.get("rain"));
        mappedEvent.put("humidity", event.get("humidity"));
        return mappedEvent;
    }

    private void validateEvent(Map<String, Object> event) {
        if (event.get("ts") == null || event.get("ss") == null || event.get("predictionTs") == null
                || event.get("latitude") == null || event.get("longitude") == null
                || event.get("temperature") == null || event.get("windSpeed") == null
                || event.get("rainProbability") == null || event.get("humidity") == null) {
            throw new IllegalArgumentException("Event is missing required fields: " + event);
        }
    }
}