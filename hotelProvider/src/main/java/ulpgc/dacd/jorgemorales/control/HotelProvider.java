package ulpgc.dacd.jorgemorales.control;

import ulpgc.dacd.jorgemorales.model.Booking;
import ulpgc.dacd.jorgemorales.model.Hotel;

import java.util.List;

public interface HotelProvider {
    List<Hotel> searchHotel(String island, Booking booking);
}
