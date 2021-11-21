package org.bp.gate;


import org.bp.gate.model.BookingInfo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class IntegrationService {
	private HashMap<String, ReservationData> reservation;
	
	@PostConstruct
	void init() {
		reservation =new HashMap<>();
	}
	
	public static class ReservationData {
		BookingInfo trainBookingInfo;
		BookingInfo carBookingInfo;
//		public boolean isReady() {
//			return trainBookingInfo!=null && carBookingInfo !=null;
//		}
		public boolean isReady() {
			return trainBookingInfo!=null;
		}
	}
	
	public synchronized boolean addBookingInfo(String bookTravelId, BookingInfo bookingInfo, String serviceType) {
		if(serviceType != null){
			ReservationData reservationData = getReservationData(bookTravelId);
			if (serviceType.equals("car"))
				reservationData.carBookingInfo = bookingInfo;
			else if (serviceType.equals("train"))
				reservationData.trainBookingInfo = bookingInfo;
			return reservationData.isReady();
		}
		return  false;
	}	
	
	
	public synchronized ReservationData getReservationData(String bookTravelId) {
		ReservationData reservationData = reservation.get(bookTravelId);
		if (reservationData ==null) {
			reservationData = new ReservationData();
			reservation.put(bookTravelId, reservationData);
		}
		return reservationData;
	}
}
