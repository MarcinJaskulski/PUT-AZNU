package org.bp.train.model;

public class TrainSeat {

	private int carriage;
	private int seat;
	private boolean isBooked;
	
	
	public TrainSeat(int carriage, int seat ) {
		this.carriage = carriage;
		this.seat = seat;
		this.isBooked = false;
	}
	
	public int getCarriage() {
		return carriage;
	}
	public void setCarriage(int carriage) {
		this.carriage = carriage;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}
}
