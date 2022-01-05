package org.bp.gate.travel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.bp.gate.chairs.OrderChairs;
import org.bp.gate.chairs.OrderChairsResponse;
import org.bp.gate.table.TableOrderSummary;
import org.bp.gate.travel.model.OrderRequest;
import org.bp.gate.travel.model.OrderSummaryResponse;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;

@Component
public class GateService extends RouteBuilder {

    @org.springframework.beans.factory.annotation.Autowired
    OrdersIdentifierService ordersIdentifierService;

    @Override
    public void configure() throws Exception {
        gateway();
        train();
        car();
    }

    private void gateway() {

        //--------------------------REST CONFIG--------------------------------------------------------------------------//
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .contextPath("/api")
                // turn on swagger api-doc
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Furniture factory API")
                .apiProperty("api.version", "1.0.0");

        //--------------------------Post REST--------------------------------------------------------------------------//

        rest("/orderFurniture").description("Furniture factory REST service") // tu przyjmuje endpoint
                .consumes("application/json")
                .produces("application/json")
                // typ wchodzacy do post i typ wychodzacy z post
                .post("/bookTravel").description("Furniture factory").type(OrderRequest.class).outType(OrderSummaryResponse.class)
                .param().name("body").type(body).description("Furniture order param").endParam()
                .responseMessage().code(200).message("The furniture order ordered successfully").endResponseMessage()
                .to("direct:orderFurniture");


        from("direct:orderFurniture").routeId("orderFurniture")
                .log("orderFurniture fired")
                .process((exchange) -> {
                    OrderSummaryResponse orderSummaryResponse = new OrderSummaryResponse();
                    String orderId = ordersIdentifierService.generateOrderId();
                    orderSummaryResponse.setId(orderId);
                    exchange.getMessage().setHeader("bookingTravelId", orderId);
                    exchange.setProperty("orderSummary", orderSummaryResponse);
                })
                .saga()
                .multicast()
                .parallelProcessing()
                .aggregationStrategy(
                        (prevEx, currentEx) -> {
                            if (currentEx.getException() != null)
                                return currentEx;
                            if (prevEx != null && prevEx.getException() != null)
                                return prevEx;

                            OrderSummaryResponse orderSummary;
                            if (prevEx == null)
                                orderSummary = currentEx.getProperty("orderSummary", OrderSummaryResponse.class);
                            else
                                orderSummary = prevEx.getMessage().getBody(OrderSummaryResponse.class);

                            Object body = currentEx.getMessage().getBody();
                            if (body instanceof OrderChairsResponse)
                                orderSummary.setChairsOrderSummary(((OrderChairsResponse) body).getReturn());
                            else if (body instanceof TableOrderSummary)
                                orderSummary.setTableOrderSummary((TableOrderSummary) body);
                            else
                                return prevEx;
                            currentEx.getMessage().setBody(orderSummary);
                            return currentEx;
                        }
                )
                .to("direct:tableOrder")
                .to("direct:chairsOrder")
                .end()
                .marshal().json()
                .log("order created").to("stream:out")
                .unmarshal().json(JsonLibrary.Jackson, OrderSummaryResponse.class);


////                .to("kafka:CarTravelReqTopic?brokers=localhost:9092")
    }
    private void train(){
        from("direct:tableOrder").routeId("tableOrder")
                .log("tableOrder fired")
                .process(exchange -> {
                    OrderRequest orderRequest = exchange.getMessage().getBody(OrderRequest.class);
                    exchange.getMessage().setBody(orderRequest.getTableOrder());
                })
                .marshal().json()
                .removeHeaders("CamelHttp*")
                .to("rest:post:tableOrder?host=localhost:8085")
                .unmarshal().json(JsonLibrary.Jackson, TableOrderSummary.class);
    }

    private void car(){
        final JaxbDataFormat jaxbOrderChairsResponse = new JaxbDataFormat(OrderChairsResponse.class.getPackage().getName());

        from("direct:chairsOrder").routeId("chairsOrder")
                .log("chairsOrder fired")
//                .saga()
//                .propagation(SagaPropagation.MANDATORY)
//                .compensation("direct:cancelCarBooking")
//                .option("bookingTravelId", simple("${exchangeProperty.bookingTravelId}"))
                .process((exchange) -> {
                    OrderRequest orderRequest = exchange.getMessage().getBody(OrderRequest.class);
                    OrderChairs orderChairs = new OrderChairs();
                    orderChairs.setArg0(orderRequest.getChairsOrder());
                    exchange.getMessage().setBody(orderChairs);
                } )
                .marshal(jaxbOrderChairsResponse)
                .to("spring-ws:http://localhost:8082/soap-api/service/chairsOrder")
                .unmarshal(jaxbOrderChairsResponse);


//        from("direct:cancelCarBooking").routeId("cancelCarBooking").log("cancelCarBooking fired")
//                .process(exchange -> {
//                    OrderSummary orderSummary = exchange.getMessage().getHeader("orderSummary", OrderSummary.class);
//                    CancelJacketProduction cancelJacketProduction = new CancelJacketProduction();
//                    cancelJacketProduction.setArg0(orderSummary.getId());
//                    exchange.getMessage().setBody(cancelJacketProduction);
//                })
//                .marshal(jaxbJacket)
//                .to("spring-ws:http://localhost:8083/soap-api/service/jacket/cancelJacketProduction");

    }

}
