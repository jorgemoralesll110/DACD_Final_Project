package ulpgc.dacd.jorgemorales;

public class Main {
    public static void main(String[] args) {
        try {
            HotelInfoReader hotelInfoReader = new HotelInfoReader();
            HotelProvider hotelProvider = new XoteloHotelProvider(hotelInfoReader);
            ActiveMQHotelSubscriber subscriber = new ActiveMQHotelSubscriber(hotelProvider);

            System.out.println("Starting hotel provider...");
            subscriber.start();
            System.out.println("Hotel provider started and subscribed to topic " + ActiveMQHotelSubscriber.topicName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}