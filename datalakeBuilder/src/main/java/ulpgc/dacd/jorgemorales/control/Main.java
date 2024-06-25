package ulpgc.dacd.jorgemorales.control;

public class Main {
    public static void main(String[] args) {
        try {
            FileEventStore eventStore = new FileEventStore();
            DatalakeEventSubscriber subscriber = new DatalakeEventSubscriber(eventStore);

            System.out.println("Starting Datalake Event Subscriber...");
            subscriber.start();
            System.out.println("Datalake Event Subscriber started and subscribed to topic " + DatalakeEventSubscriber.topicName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
