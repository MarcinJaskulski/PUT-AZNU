package org.bp.gate;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.bp.gate.exceptions.GateException;
import org.bp.gate.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.apache.camel.model.rest.RestParamType.body;


@Component
public class GateService extends RouteBuilder {

    @org.springframework.beans.factory.annotation.Autowired
    org.bp.gate.MessageIdentifierService messageIdentifierService;

    @org.springframework.beans.factory.annotation.Autowired
    IntegrationService integrationService;

    @Override
    public void configure() throws Exception {
        messageExceptionHandlers();
        gateway();
    }

    private void messageExceptionHandlers() {
        onException(GateException.class)
                .process((exchange) -> {
                            ExceptionResponse er = new ExceptionResponse();
                            er.setTimestamp(OffsetDateTime.now());
                            Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                            er.setMessage(cause.getMessage());
                            exchange.getMessage().setBody(er);
                        }
                )
                .marshal().json()
                .to("stream:out")
                .setHeader("serviceType", constant("flight"))
                .to("kafka:TravelBookingFailTopic?brokers=localhost:9092")
                .handled(true);
    }

    private void gateway() {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .contextPath("/api")
                // turn on swagger api-doc
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Micro Travel booking API")
                .apiProperty("api.version", "1.0.0");

        rest("/gate").description("Micro Travel booking REST service") // tu przyjmuje endpoint
                .consumes("application/json")
                .produces("application/json")
                // typ wchodzacy do post i typ wychodzacy z post
                .post("/bookTravel").description("Book Travel").type(BookTravelRequest.class).outType(BookingInfo.class)
                .param().name("body").type(body).description("The travel to book").endParam()
                .responseMessage().code(200).message("Hello successfully sent").endResponseMessage()
                .to("direct:BookTravel");
//                .to("direct:notification");


        from("direct:BookTravel").routeId("bookTravel")
                .log("bookTravel fired")
                .process((exchange) -> {
                    exchange.getMessage().setHeader("bookingTravelId", messageIdentifierService.getMessageIdentifier());
                })
                .to("direct:TravelBookRequest");

        from("direct:TravelBookRequest").routeId("TravelBookRequest")
                .log("TravelBookRequest fired")
                .process( (x) ->{
                    int a = 8;
                })
                .marshal().json()
                .to("kafka:TravelReqTopic?brokers=localhost:9092")
                .to("direct:integrateRequest");

        // Odebranie od mikrousług
        from("kafka:bookResponseTopic?brokers=localhost:9092").routeId("bookResponseTopic")
                .log("bookResponseTopic fired")
                .unmarshal().json(JsonLibrary.Jackson, BookingInfo.class)
                .process( (x) ->{
                    int a = 8;
                })
                .marshal().json()
                .to("direct:integrateRequest");



        from("direct:integrateRequest").routeId("integrateRequest")
                .log("fired integrateRequest")
                .unmarshal().json(JsonLibrary.Jackson, BookingInfo.class)
                .process(
                        (exchange) -> {
                            String bookingTravelId = exchange.getMessage().getHeader("bookingTravelId", String.class);
                            BookingInfo bookingInfo = exchange.getMessage().getBody(BookingInfo.class);

                            boolean isReady = false;
                            if(bookingInfo.containsValue()){ // dla faktyzcnego komunikatu
                                isReady = integrationService.addBookingInfo(
                                        bookingTravelId,
                                        bookingInfo,
                                        exchange.getMessage().getHeader("serviceType", String.class));

                                exchange.getMessage().setBody(bookingInfo);
                            }
                            else // dla wątku, który ma zwrócić wynik
                                isReady = integrationService.getReservationData(bookingTravelId).isReady();
                            exchange.getMessage().setHeader("isReady", isReady);
                        }
                )
                .marshal().json()
                .choice()
                .when(header("isReady").isEqualTo(true)).to("direct:notification")
                .endChoice()
                .log("Poszlo");


        // Odesłanie odpowiedzi - // TO NIE DZIALA !!!! :(
        from("direct:notification").routeId("notification")
                .log("fired notification")
//                .marshal().json()
                .process((exchange) -> {
                    exchange.getMessage().setHeader("work", "work");
                    exchange.getMessage().setBody(
                            new BookingInfo("aaa", 1, 4)
                    );
                })
                .marshal().json()
                .removeHeaders("CamelHttp*")
                .log("created")
                .to("stream:out")
                .unmarshal().json(JsonLibrary.Jackson, BookingInfo.class);
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
//                .end();
//                .marshal().json();
//                .to("stream:out");



//        from("direct:notification").routeId("notification")
//                .log("fired notification")
////                .unmarshal().json(JsonLibrary.Jackson, BookingInfo.class)
//                .process( (exchange) ->{
//                    exchange.getMessage().setBody(new BookingInfo("aaa", 1, 4));
//                })
//                .marshal().json();
//                .to("stream:out");
//
//                .marshal().json()
//                .end();

    }
}
