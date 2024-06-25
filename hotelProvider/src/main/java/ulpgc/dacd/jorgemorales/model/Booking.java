package ulpgc.dacd.jorgemorales.model;

import java.time.Instant;

public class Booking {
    private String checkInDate;
    private String checkOutDate;

    public Booking(Instant checkInDate, Instant checkOutDate) {
        this.checkInDate = String.valueOf(checkInDate);
        this.checkOutDate = String.valueOf(checkOutDate);
    }


    public String getCheckInDate() {
        return checkInDate;
    }



    public String getCheckOutDate() {
        return checkOutDate;
    }

}

