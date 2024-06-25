package ulpgc.dacd.jorgemorales.model;

import java.time.Instant;

public class Booking {
    private String checkInDate;
    private String checkOutDate;

    public Booking(Instant checkInDate, Instant checkOutDate) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }


    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}

