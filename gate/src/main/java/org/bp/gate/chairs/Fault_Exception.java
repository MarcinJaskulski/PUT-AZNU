
package org.bp.gate.chairs;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.0.2
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "Fault", targetNamespace = "http://chairsFactory.bp.org/")
public class Fault_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private Fault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public Fault_Exception(String message, Fault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public Fault_Exception(String message, Fault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: org.bp.chairsfactory.Fault
     */
    public Fault getFaultInfo() {
        return faultInfo;
    }

}
