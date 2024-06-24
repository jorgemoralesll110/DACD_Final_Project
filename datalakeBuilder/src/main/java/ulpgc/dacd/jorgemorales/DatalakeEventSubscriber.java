package ulpgc.dacd.jorgemorales;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.IOException;

public class DatalakeEventSubscriber implements EventSubscriber, MessageListener{
    private static final String brokerURL = "tcp://localhost:61616";
    private static final String topicName = "prediction.Weather";
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
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String eventJson = ((TextMessage) message).getText();
                System.out.println("Event received: " + eventJson);
                eventStore.saveEvent(eventJson);
            }
        } catch (JMSException e) {
            e.printStackTrace();
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
