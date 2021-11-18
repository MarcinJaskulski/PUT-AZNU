package org.bp.gate.model;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Objects;

public class Point {
	
    private String city;
    private XMLGregorianCalendar date;
    
    public boolean isCorrect() {
		
		if(this.getCity() != null && this.getCity() != "" )
			return true;
		return false;
	}
    
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public XMLGregorianCalendar getDate() {
		return date;
	}
	public void setDate(XMLGregorianCalendar date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Point point = (Point) o;
		return Objects.equals(this.city, point.city) &&
				Objects.equals(this.getDate(), point.getDate());
	}
}
