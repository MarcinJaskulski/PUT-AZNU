
package org.bp.gate.chairs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.0.2
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ChairsService", targetNamespace = "http://chairsFactory.bp.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ChairsService {


    /**
     * 
     * @param arg0
     * @return
     *     returns org.bp.chairsfactory.ChairsOrderSummary
     * @throws Fault_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getChairsOrderSummary", targetNamespace = "http://chairsFactory.bp.org/", className = "org.bp.chairsfactory.GetChairsOrderSummary")
    @ResponseWrapper(localName = "getChairsOrderSummaryResponse", targetNamespace = "http://chairsFactory.bp.org/", className = "org.bp.chairsfactory.GetChairsOrderSummaryResponse")
    public ChairsOrderSummary getChairsOrderSummary(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws Fault_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns org.bp.chairsfactory.ChairsOrderSummary
     * @throws Fault_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "orderChairs", targetNamespace = "http://chairsFactory.bp.org/", className = "org.bp.chairsfactory.OrderChairs")
    @ResponseWrapper(localName = "orderChairsResponse", targetNamespace = "http://chairsFactory.bp.org/", className = "org.bp.chairsfactory.OrderChairsResponse")
    public ChairsOrderSummary orderChairs(
        @WebParam(name = "arg0", targetNamespace = "")
        ChairsOrder arg0)
        throws Fault_Exception
    ;

    /**
     * 
     * @param arg0
     * @throws Fault_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "cancelChairsOrder", targetNamespace = "http://chairsFactory.bp.org/", className = "org.bp.chairsfactory.CancelChairsOrder")
    @ResponseWrapper(localName = "cancelChairsOrderResponse", targetNamespace = "http://chairsFactory.bp.org/", className = "org.bp.chairsfactory.CancelChairsOrderResponse")
    public void cancelChairsOrder(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0)
        throws Fault_Exception
    ;

}
