package ulpgc.dacd.jorgemorales.control;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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