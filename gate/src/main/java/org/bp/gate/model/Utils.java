package org.bp.gate.model;

import java.math.BigDecimal;

public class Utils {
    static public BookingInfo prepareBookingInfo(String bookingId, Integer travelTimeByTrain, Integer travelTimeByCar) {
        BookingInfo bookingInfo = new BookingInfo();
        bookingInfo.setId(bookingId);
        bookingInfo.setTravelTimeByTrain(travelTimeByTrain);
        bookingInfo.setTravelTimeByCar(travelTimeByCar);
        return bookingInfo;
    }

}
