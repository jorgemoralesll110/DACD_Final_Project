package ulpgc.dacd.jorgemorales;

public class Main {
    public static void main(String[] args) {
        EventStore eventStore = new FileEventStore();
        EventSubscriber eventSubscriber = new DatalakeEventSubscriber(eventStore);

        try {
            System.out.println("Starting EventSubscriber...");
            eventSubscriber.start();
            System.out.println("DatalakeBuilder started and subscribed to prediction.Weather topic.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

