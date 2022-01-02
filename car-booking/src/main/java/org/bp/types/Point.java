package org.bp.types;

import javax.xml.datatype.XMLGregorianCalendar;

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
}
