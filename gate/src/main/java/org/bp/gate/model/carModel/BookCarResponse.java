//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.19 at 09:02:06 AM CET 
//


package org.bp.gate.model.carModel;

import org.bp.gate.model.BookingInfoResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bookFligthResponse", propOrder = {
    "_return"
})
public class BookCarResponse {

    @XmlElement(name = "return")
    protected Car _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link BookingInfoResponse }
     *     
     */
    public Car getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingInfoResponse }
     *     
     */
    public void setReturn(Car value) {
        this._return = value;
    }

}
