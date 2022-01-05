
package org.bp.gate.chairs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.bp.chairsfactory package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CancelChairsOrder_QNAME = new QName("http://chairsFactory.bp.org/", "cancelChairsOrder");
    private final static QName _CancelChairsOrderResponse_QNAME = new QName("http://chairsFactory.bp.org/", "cancelChairsOrderResponse");
    private final static QName _GetChairsOrderSummary_QNAME = new QName("http://chairsFactory.bp.org/", "getChairsOrderSummary");
    private final static QName _GetChairsOrderSummaryResponse_QNAME = new QName("http://chairsFactory.bp.org/", "getChairsOrderSummaryResponse");
    private final static QName _OrderChairs_QNAME = new QName("http://chairsFactory.bp.org/", "orderChairs");
    private final static QName _OrderChairsResponse_QNAME = new QName("http://chairsFactory.bp.org/", "orderChairsResponse");
    private final static QName _Fault_QNAME = new QName("http://chairsFactory.bp.org/", "Fault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.bp.chairsfactory
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelChairsOrder }
     * 
     */
    public CancelChairsOrder createCancelChairsOrder() {
        return new CancelChairsOrder();
    }

    /**
     * Create an instance of {@link CancelChairsOrderResponse }
     * 
     */
    public CancelChairsOrderResponse createCancelChairsOrderResponse() {
        return new CancelChairsOrderResponse();
    }

    /**
     * Create an instance of {@link GetChairsOrderSummary }
     * 
     */
    public GetChairsOrderSummary createGetChairsOrderSummary() {
        return new GetChairsOrderSummary();
    }

    /**
     * Create an instance of {@link GetChairsOrderSummaryResponse }
     * 
     */
    public GetChairsOrderSummaryResponse createGetChairsOrderSummaryResponse() {
        return new GetChairsOrderSummaryResponse();
    }

    /**
     * Create an instance of {@link OrderChairs }
     * 
     */
    public OrderChairs createOrderChairs() {
        return new OrderChairs();
    }

    /**
     * Create an instance of {@link OrderChairsResponse }
     * 
     */
    public OrderChairsResponse createOrderChairsResponse() {
        return new OrderChairsResponse();
    }

    /**
     * Create an instance of {@link Fault }
     * 
     */
    public Fault createFault() {
        return new Fault();
    }

    /**
     * Create an instance of {@link ChairsOrderSummary }
     * 
     */
    public ChairsOrderSummary createChairsOrderSummary() {
        return new ChairsOrderSummary();
    }

    /**
     * Create an instance of {@link ChairsOrder }
     * 
     */
    public ChairsOrder createChairsOrder() {
        return new ChairsOrder();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelChairsOrder }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CancelChairsOrder }{@code >}
     */
    @XmlElementDecl(namespace = "http://chairsFactory.bp.org/", name = "cancelChairsOrder")
    public JAXBElement<CancelChairsOrder> createCancelChairsOrder(CancelChairsOrder value) {
        return new JAXBElement<CancelChairsOrder>(_CancelChairsOrder_QNAME, CancelChairsOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelChairsOrderResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CancelChairsOrderResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://chairsFactory.bp.org/", name = "cancelChairsOrderResponse")
    public JAXBElement<CancelChairsOrderResponse> createCancelChairsOrderResponse(CancelChairsOrderResponse value) {
        return new JAXBElement<CancelChairsOrderResponse>(_CancelChairsOrderResponse_QNAME, CancelChairsOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChairsOrderSummary }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetChairsOrderSummary }{@code >}
     */
    @XmlElementDecl(namespace = "http://chairsFactory.bp.org/", name = "getChairsOrderSummary")
    public JAXBElement<GetChairsOrderSummary> createGetChairsOrderSummary(GetChairsOrderSummary value) {
        return new JAXBElement<GetChairsOrderSummary>(_GetChairsOrderSummary_QNAME, GetChairsOrderSummary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChairsOrderSummaryResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetChairsOrderSummaryResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://chairsFactory.bp.org/", name = "getChairsOrderSummaryResponse")
    public JAXBElement<GetChairsOrderSummaryResponse> createGetChairsOrderSummaryResponse(GetChairsOrderSummaryResponse value) {
        return new JAXBElement<GetChairsOrderSummaryResponse>(_GetChairsOrderSummaryResponse_QNAME, GetChairsOrderSummaryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderChairs }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrderChairs }{@code >}
     */
    @XmlElementDecl(namespace = "http://chairsFactory.bp.org/", name = "orderChairs")
    public JAXBElement<OrderChairs> createOrderChairs(OrderChairs value) {
        return new JAXBElement<OrderChairs>(_OrderChairs_QNAME, OrderChairs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderChairsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrderChairsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://chairsFactory.bp.org/", name = "orderChairsResponse")
    public JAXBElement<OrderChairsResponse> createOrderChairsResponse(OrderChairsResponse value) {
        return new JAXBElement<OrderChairsResponse>(_OrderChairsResponse_QNAME, OrderChairsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Fault }{@code >}
     */
    @XmlElementDecl(namespace = "http://chairsFactory.bp.org/", name = "Fault")
    public JAXBElement<Fault> createFault(Fault value) {
        return new JAXBElement<Fault>(_Fault_QNAME, Fault.class, null, value);
    }

}
