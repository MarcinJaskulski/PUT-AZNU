package org.bp.car;

import org.bp.types.Car;
import org.bp.types.Fault;
import org.bp.types.CarBooking;

import java.util.HashMap;

@javax.jws.WebService
@org.springframework.stereotype.Service
public class CarService {
	
	HashMap<String, Car> bookedCars = new HashMap<>();

	public Car bookCar(CarBooking carBooking) throws Fault{
		if(!carBooking.isCorrect())
			throw new Fault(1, "Zle uzupełnione dane");		
		
		Car car = carBooking.getCar();
		if(car.getNumberOfSeats() > 12)
			throw new Fault(3, "Nieobsługiwana liczba miejsc");
		else if(car.getNumberOfSeats() > 5) 
			car.setName("Bus");
		else 
			car.setName("Auto Osobowe");
		
		bookedCars.put(carBooking.getReservationId(), car);
		
		return car;
	}

	public Car getBookingCar(String reservationId) throws Fault{
		if(!bookedCars.containsKey(reservationId))
			throw new Fault(2, "Auto o podanym Id nie istnieje");
		return bookedCars.get(reservationId);
	}
	
	public Car cancelBooking(int carId) throws Fault{
		if(!bookedCars.containsKey(carId))
			throw new Fault(2, "Auto o podanym Id nie istnieje");
		Car car = bookedCars.get(carId);
		bookedCars.remove(carId);
		return car;			
	}
}

