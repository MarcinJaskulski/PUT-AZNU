package org.bp.gate.model;

public class Utils {
    static public BookingInfoResponse prepareBookingInfo(String bookingId, Integer travelTimeByTrain, Integer travelTimeByCar) {
        BookingInfoResponse bookingInfo = new BookingInfoResponse();
        bookingInfo.setId(bookingId);
        bookingInfo.setTravelTimeByTrain(travelTimeByTrain);
        bookingInfo.setTravelTimeByCar(travelTimeByCar);
        return bookingInfo;
    }

}
