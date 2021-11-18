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
                .post("/bookTravel").description("Hello Message").type(BookTravelRequest.class).outType(MessageInfo.class)
                .param().name("body").type(body).description("The travel to book").endParam()
                .responseMessage().code(200).message("Hello successfully sent").endResponseMessage()
                .to("direct:BookTravel");

        from("direct:BookTravel").routeId("bookTravel")
                .log("bookTravel fired")
                .process((exchange) -> {
                    exchange.getMessage().setHeader("bookingTravelId", messageIdentifierService.getMessageIdentifier());
                })
                .to("direct:TravelBookRequest");

        from("direct:TravelBookRequest").routeId("TravelBookRequest")
                .log("brokerTopic fired")
                .marshal().json()
                .to("kafka:TravelReqTopic?brokers=localhost:9092");


    }
}
