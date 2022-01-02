package org.bp.gate;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class BookingIdentifierService {
	HashMap<String, BookingIds> bookingIdsMap =  new HashMap<>();

	public String generateBookingId() {
		String bookingID=UUID.randomUUID().toString();
		BookingIds bookingIds= new BookingIds();
		bookingIdsMap.put(bookingID, bookingIds);
		return bookingID;
	}

	public void assignTrainBookingId(String travelBookingId, int trainBookingId) {
		bookingIdsMap.get(travelBookingId).setTrainBookingId(trainBookingId);
	}

	public void assignCarBookingId(String travelBookingId, int carBookingId) {
		bookingIdsMap.get(travelBookingId).setCarBoogingId(carBookingId);
	}

	public int getTrainBookingId(String travelBookingId) {
		return bookingIdsMap.get(travelBookingId).getTrainBookingId();
	}

	public int getCarBookingId(String travelBookingId) {
		return bookingIdsMap.get(travelBookingId).getCarBoogingId();
	}

	public static class BookingIds{
		private int hotelBookingId;
		private int carBoogingId;

		public int getTrainBookingId() {
			return hotelBookingId;
		}
		public void setTrainBookingId(int hotelBookingId) {
			this.hotelBookingId = hotelBookingId;
		}
		public int getCarBoogingId() {
			return carBoogingId;
		}
		public void setCarBoogingId(int flightBoogingId) {
			this.carBoogingId = flightBoogingId;
		}
	}
}