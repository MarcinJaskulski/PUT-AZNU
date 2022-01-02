package org.bp.gate.model.carModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Car", propOrder = {
		"name",
		"numberOfSeats"
})
public class Car {

	private String name;
	private int numberOfSeats;

	public boolean isCorrect() {
		if(this.getNumberOfSeats() > 0)
			return true;
		return false;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
}
