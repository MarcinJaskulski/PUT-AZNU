package org.bp.gate.model.carModel;

import org.bp.gate.model.Point;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CarBooking", propOrder = {
		"reservationId",
		"from",
		"to",
		"car"
})

public class CarBooking {
	private String reservationId;
	private Point from;
	private Point to;
	private Car car;

	public CarBooking() {
	}

	public CarBooking(String reservationId, Point from, Point to) {
		this.reservationId = reservationId;
		this.from = from;
		this.to = to;
	}

	public boolean isCorrect() {
		
		if(this.getFrom().isCorrect() &&
			this.getTo().isCorrect() &&
		    this.getCar() != null && this.getCar().isCorrect() &&
			this.reservationId != null && this.reservationId != ""
		){
				return true;
		}
		return false;
	}

	public String getReservationId() {
		return reservationId;
	}
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	public Point getFrom() {
		return from;
	}
	public void setFrom(Point from) {
		this.from = from;
	}
	public Point getTo() {
		return to;
	}
	public void setTo(Point to) {
		this.to = to;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
}
