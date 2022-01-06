package org.bp.gate.travel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.bp.gate.chairs.*;
import org.bp.gate.table.TableOrderSummary;
import org.bp.gate.travel.model.GateException;
import org.bp.gate.travel.model.OrderRequest;
import org.bp.gate.travel.model.OrderSummaryResponse;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

@Component
public class GateService extends RouteBuilder {

    @org.springframework.beans.factory.annotation.Autowired
    OrdersIdentifierService ordersIdentifierService;

    final String chairsOrderUrl = "http://localhost:8082/soap-api/service/chairsOrder";

    @Override
    public void configure() throws Exception {
        gateway();
        table();
        chairs();
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

        rest("/orderFurniture").description("Order furniture") // tu przyjmuje endpoint
                .consumes("application/json")
                .produces("application/json")
                // typ wchodzacy do post i typ wychodzacy z post
                .post("/order").description("Furniture factory").type(OrderRequest.class).outType(OrderSummaryResponse.class)
                .param().name("body").type(body).description("Furniture order param").endParam()
                .responseMessage().code(200).message("The furniture order ordered successfully").endResponseMessage()
                .responseMessage().code(400).responseModel(GateException.class).message("Post order exception").endResponseMessage()
                .to("direct:orderFurniture");

        from("direct:orderFurniture").routeId("orderFurniture")
                .log("orderFurniture fired")
                .process((exchange) -> {
                    OrderSummaryResponse orderSummaryResponse = new OrderSummaryResponse();
                    String orderId = ordersIdentifierService.generateOrderId();
                    orderSummaryResponse.setId(orderId);
                    exchange.setProperty("orderId", orderId);
                    exchange.setProperty("orderSummaryResponse", orderSummaryResponse);
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
                                orderSummary = currentEx.getProperty("orderSummaryResponse", OrderSummaryResponse.class);
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
//                .to("kafka:tableOrderTopic?brokers=localhost:9092")
//                .to("kafka:chairsOrderTopic?brokers=localhost:9092")
                .end()
                .marshal().json()
                .log("order created").to("stream:out")
                .unmarshal().json(JsonLibrary.Jackson, OrderSummaryResponse.class);

        //--------------------------Get REST--------------------------------------------------------------------------//

        rest("/orderFurniture").description("Get order summary")
                .produces("application/json")
                .get("/order/{orderId}")
                .outType(OrderSummaryResponse.class)
                .param().name("orderId").type(path).description("Order Id").endParam()
                .responseMessage().code(200).message("The furniture order get successfully").endResponseMessage()
                .responseMessage().code(400).responseModel(GateException.class).message("Get order exception").endResponseMessage()
                .to("direct:getOrderFurniture");

        from("direct:getOrderFurniture")
                .routeId("getOrderFurniture")
                .log("getOrderFurniture fired")
                .process(exchange -> {
                    String orderId = exchange.getMessage().getHeader("orderId", String.class);
                    OrderSummaryResponse orderSummaryResponse = new OrderSummaryResponse();
                    orderSummaryResponse.setId(orderId);
                    exchange.setProperty("orderSummary", orderSummaryResponse);
                })
                .saga()
                .multicast()
                .parallelProcessing()
                .aggregationStrategy((prevEx, currentEx) -> {
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
                    if (body instanceof GetChairsOrderSummaryResponse)
                        orderSummary.setChairsOrderSummary(((GetChairsOrderSummaryResponse) body).getReturn());
                    else if (body instanceof TableOrderSummary)
                        orderSummary.setTableOrderSummary((TableOrderSummary) body);
                    else
                        return prevEx;
                    currentEx.getMessage().setBody(orderSummary);
                    return currentEx;
                })
                .to("direct:getTableOrder")
                .to("direct:getChairsOrder")
//                .to("kafka:getTableOrderTopic?brokers=localhost:9092")
//                .to("kafka:getChairsOrderTopic?brokers=localhost:9092")
                .end()
                .marshal().json()
                .log("got order").to("stream:out")
                .unmarshal().json(JsonLibrary.Jackson, OrderSummaryResponse.class);


    }
    private void table(){
        //--------------------------Post REST--------------------------------------------------------------------------//

        from("direct:tableOrder")
//                from("kafka:tableOrderTopic?brokers=localhost:9092")
                .routeId("tableOrder").log("tableOrder fired")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .compensation("direct:cancelTableOrder")
                .option("orderSummary", simple("${exchangeProperty.orderSummaryResponse}"))
                .process(exchange -> {
                    OrderRequest orderRequest = exchange.getMessage().getBody(OrderRequest.class);
                    exchange.getMessage().setBody(orderRequest.getTableOrder());
                })
                .marshal().json()
                .removeHeaders("CamelHttp*")
                .to("rest:post:tableOrder?host=localhost:8085")
                .unmarshal().json(JsonLibrary.Jackson, TableOrderSummary.class)
                .to("stream:out")
                .process((exchange) -> {
                    TableOrderSummary tableOrderSummary = exchange.getMessage().getBody(TableOrderSummary.class);
                    String orderId = exchange.getProperty("orderId", String.class);
                    ordersIdentifierService.assignTableId(orderId, tableOrderSummary.getId());
                });

        from("direct:cancelTableOrder")
                .routeId("cancelTableOrder").log("cancelTableOrder fired")
                .process(exchange -> {
                    OrderSummaryResponse orderSummaryResponse = exchange.getMessage().getHeader("orderSummary", OrderSummaryResponse.class);
                    String tableOrderId = ordersIdentifierService.getTableOrderId(orderSummaryResponse.getId());
                    exchange.getMessage().setHeader("tableOrderId", tableOrderId);
                })
                .marshal().json()
                .removeHeaders("CamelHttp*")
                .toD("rest:delete:cancelTableOrder/${header.tableOrderId}?host=localhost:8085");

        //--------------------------Get REST--------------------------------------------------------------------------//
        from("direct:getTableOrder")
//            from("kafka:getTableOrderTopic?brokers=localhost:9092")
                .routeId("getTableOrder").log("getTableOrder fired")
                .process(exchange -> {
                    String mainOrderId = exchange.getMessage().getHeader("orderId", String.class);
                    String tableOrderId = ordersIdentifierService.getTableOrderId(mainOrderId);
                    exchange.getMessage().setHeader("tableOrderId", tableOrderId);
                })
                .marshal().json()
                .removeHeaders("CamelHttp*")
                .toD("rest:get:getTableOrder/${header.tableOrderId}?host=localhost:8085")
                .unmarshal().json(JsonLibrary.Jackson, TableOrderSummary.class);
    }

    private void chairs(){
        final JaxbDataFormat jaxbOrderChairsResponse = new JaxbDataFormat(OrderChairsResponse.class.getPackage().getName());

        //--------------------------Post SOAP--------------------------------------------------------------------------//
        from("direct:chairsOrder")
//            from("kafka:chairsOrderTopic?brokers=localhost:9092")
                .routeId("chairsOrder").log("chairsOrder fired")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .compensation("direct:cancelChairsOrder")
                .option("orderSummary", simple("${exchangeProperty.orderSummaryResponse}"))
                .process((exchange) -> {
                    OrderRequest orderRequest = exchange.getMessage().getBody(OrderRequest.class);
                    OrderChairs orderChairs = new OrderChairs();
                    orderChairs.setArg0(orderRequest.getChairsOrder());
                    exchange.getMessage().setBody(orderChairs);
                } )
                .marshal(jaxbOrderChairsResponse)
                .to("spring-ws:" + chairsOrderUrl)
                .to("stream:out")
                .unmarshal(jaxbOrderChairsResponse)
                .process((exchange) -> {
                    OrderChairsResponse chairsOrderSummary = exchange.getMessage().getBody(OrderChairsResponse.class);
                    String orderId = exchange.getProperty("orderId", String.class);
                    ordersIdentifierService.assignChairsOrderId(orderId, chairsOrderSummary.getReturn().getId());
                });


        from("direct:cancelChairsOrder").routeId("cancelChairsOrder")
                .log("cancelChairsOrder fired")
                .process(exchange -> {
                    OrderSummaryResponse orderSummaryResponse = exchange.getMessage().getHeader("orderSummary", OrderSummaryResponse.class);
                    String chairOrderId = ordersIdentifierService.getChairsOrderId(orderSummaryResponse.getId());
                    CancelChairsOrder cancelChairsOrder = new CancelChairsOrder();
                    cancelChairsOrder.setArg0(chairOrderId);
                    exchange.getMessage().setBody(cancelChairsOrder);
                })
                .marshal(jaxbOrderChairsResponse)
                .to("spring-ws:" + chairsOrderUrl + "/cancelChairsOrder");

        //--------------------------Get SOAP--------------------------------------------------------------------------//

        from("direct:getChairsOrder")
//            from("kafka:getChairsOrderTopic?brokers=localhost:9092")
                .routeId("getChairsOrder").log("getChairsOrder fired")
                .process(exchange -> {
                    String mainOrderId = exchange.getMessage().getHeader("orderId", String.class);
                    String chairsOrderId = ordersIdentifierService.getChairsOrderId(mainOrderId);
                    GetChairsOrderSummary getChairsOrderSummary = new GetChairsOrderSummary();
                    getChairsOrderSummary.setArg0(chairsOrderId);
                    exchange.getMessage().setBody(getChairsOrderSummary);
                })
                .marshal(jaxbOrderChairsResponse)
                .to("spring-ws:" + chairsOrderUrl+ "/getChairsOrderSummary")
                .unmarshal(jaxbOrderChairsResponse);
    }
}
