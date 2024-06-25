package ulpgc.dacd.jorgemorales.control;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class BrokerConnection {
    private static final String brokerURL = "tcp://localhost:61616";
    private Connection connection;
    private Session session;

    public BrokerConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public Session getSession() {
        return session;
    }

    public void close() throws JMSException {
        session.close();
        connection.close();
    }
}
