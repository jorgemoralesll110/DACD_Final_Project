package ulpgc.dacd.jorgemorales.control;

public class Main {

    public static void main(String[] args) {
        try {
            HotelInfoReader hotelInfoReader = new HotelInfoReader();
            HotelProvider hotelProvider = new XoteloHotelProvider(hotelInfoReader);
            ActiveMQHotelSubscriber subscriber = new ActiveMQHotelSubscriber(hotelProvider);

            System.out.println("Starting HotelProvider...");
            subscriber.start();
            System.out.println("HotelProvider started and subscribed to hotel.Availability topic");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}