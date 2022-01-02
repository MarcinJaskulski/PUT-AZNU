package org.bp.gate;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.bp.gate.exceptions.GateException;
import org.bp.gate.model.BookTravelRequest;
import org.bp.gate.model.BookingInfoResponse;
import org.bp.gate.model.ExceptionResponse;
import org.bp.gate.model.carModel.CarBooking;
import org.springframework.stereotype.Component;



import java.time.OffsetDateTime;

import static org.apache.camel.model.rest.RestParamType.body;


@Component
public class GateService extends RouteBuilder {

    @org.springframework.beans.factory.annotation.Autowired
    org.bp.gate.BookingIdentifierService bookingIdentifierService;

    @org.springframework.beans.factory.annotation.Autowired
    IntegrationService integrationService;

    @Override
    public void configure() throws Exception {
        messageExceptionHandlers();
        gateway();
        car();
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
//                .to("kafka:TravelBookingFailTopic?brokers=localhost:9092")
                .handled(true);
    }

    private void gateway() {

//        final JaxbDataFormat jaxbPrescription = new JaxbDataFormat(RegisterPrescriptionResponse.class.getPackage().getName());


        //--------------------------REST CONFIG--------------------------------------------------------------------------//
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

        //--------------------------Post REST--------------------------------------------------------------------------//

        rest("/gate").description("Micro Travel booking REST service") // tu przyjmuje endpoint
                .consumes("application/json")
                .produces("application/json")
                // typ wchodzacy do post i typ wychodzacy z post
                .post("/bookTravel").description("Book Travel").type(BookTravelRequest.class).outType(BookingInfoResponse.class)
                .param().name("body").type(body).description("The travel to book").endParam()
                .responseMessage().code(200).message("The trip was booked successfully").endResponseMessage()
                .to("direct:SetId");




////                .to("kafka:CarTravelReqTopic?brokers=localhost:9092")


        //--------------------------POST REST trainBooking --------------------------------------------------------------------------//

        from("direct:trainBooking").routeId("trainBooking")
                .log("trainBooking fired")
                .marshal().json()
                .removeHeaders("CamelHttp*")
                .to("rest:post:trainBooking?host=localhost:8089")
                .unmarshal().json();

        from("direct:mock").routeId("mock")
                .log("fired mock")
                .to("stream:out");



        //--------------------------POST WSSOAP carBooking-------------------------------------------------------------------//

        final JaxbDataFormat jaxbCarBooking = new JaxbDataFormat(CarBooking.class.getPackage().getName());

        from("direct:carBooking").routeId("carBooking")
                .log("carBooking fired")
//                .saga()
//                .propagation(SagaPropagation.MANDATORY)
//                .compensation("direct:cancelPrescription").option("bookingTravelId", simple("${exchangeProperty.bookingTravelId}"))
//                .process((exchange) -> {
//                        exchange.getMessage().setBodvy(
//                        Utils.prepareRegisterPrescription(exchange.getMessage().getBody(MedicalRequest.class)));
//                } )
                .marshal(jaxbCarBooking)
                .to("spring-ws:http://localhost:8081/soap-api/service/car")
                .to("stream:out")
                .unmarshal(jaxbCarBooking);
//                .process((exchange) -> {
//                    RegisterPrescriptionResponse registerPrescriptionResponse = exchange.getMessage().getBody(RegisterPrescriptionResponse.class);
//                    exchange.setProperty("registerPrescriptionResponse", registerPrescriptionResponse);
//                    String medicalId=exchange.getProperty("medicalId", String.class);
//                    medicalIdentifierService.assignPrescriptionId(medicalId, registerPrescriptionResponse.getReturn().getId());
//                });

        //--------------------------Cancel carBooking--------------------------------------------------------------------------//

        from("direct:cancelCarBooking").routeId("cancelCarBooking")
                .log("cancelCarBooking fired")
//                .process((exchange) -> {
//
//                    String bookingTravelId = exchange.getMessage().getHeader("bookingTravelId", String.class);
//                    int prescriptionId = medicalIdentifierService.getPrescriptionId(bookingTravelId);
//                    CancelPrescription cancelPrescription = new CancelPrescription();
//                    cancelPrescription.setArg0(prescriptionId);
//                    exchange.getMessage().setBody(cancelPrescription);
//                } )
//                .marshal(jaxbPrescription)
//                .to("spring-ws:http://localhost:8081/soap-api/service/prescription")
                .to("stream:out")
//                .unmarshal(jaxbPrescription)
        ;




        //--------------------------ENDPOINTS--------------------------------------------------------------------------//

        from("direct:SetId").routeId("setId")
                .log("SetId fired")
                .process((exchange) -> {
                    String bookingInfoId = bookingIdentifierService.generateBookingId();
                    BookingInfoResponse bookingInfo = exchange.getMessage().getBody(BookingInfoResponse.class);
                    bookingInfo.setId(bookingInfoId);
                    exchange.getMessage().setBody( bookingInfo);
                    exchange.getMessage().setHeader("bookingTravelId", bookingInfoId);
                })
                .to("direct:travelBook")
                .end();

        from("direct:travelBook").routeId("travelBook")
                .log("fired travelBook")
                .saga()
                .multicast()
                .parallelProcessing()
                .aggregationStrategy(
                        (prevEx, currentEx) -> {
                            if (currentEx.getException() != null)
                                return currentEx;
                            if (prevEx != null && prevEx.getException() != null)
                                return prevEx;
                            return currentEx;
                        }
                )
                .to("direct:trainBooking")
                .to("direct:carBooking")
                .end();
//
//                .process(
//                        (exchange) -> {
//                            String bookingTravelId = exchange.getMessage().getHeader("bookingTravelId", String.class);
//                            BookingInfo bookingInfo = exchange.getMessage().getBody(BookingInfo.class);
//
//                            integrationService.addBookingInfo(
//                                        bookingTravelId,
//                                        bookingInfo,
//                                        exchange.getMessage().getHeader("serviceType", String.class));
//
//                            IntegrationService.ReservationData reservationData = integrationService.getReservationData(bookingTravelId);
//
//                            exchange.getMessage().setHeader("work", "work1");
//                            exchange.getMessage().setHeader("isReady", reservationData.isReady());
//
//                            int travelTimeByTrain = -1;
//                            int travelTimeByCar = -1;
//                            if(reservationData.trainBookingInfo != null)
//                                travelTimeByTrain = reservationData.trainBookingInfo.getTravelTimeByTrain();
//                            if(reservationData.carBookingInfo != null)
//                                travelTimeByCar = reservationData.carBookingInfo.getTravelTimeByCar();
//
//                            exchange.getMessage().setBody(
//                                    new BookingInfo(
//                                            bookingTravelId,
//                                            travelTimeByTrain,
//                                            travelTimeByCar));
//                        })
//                .marshal().json()
//                .log("Poszlo")
//                .choice()
//                .when(header("isReady").isEqualTo(true)).to("direct:notification")
//                .otherwise().to("direct:integrateRequest")
//                .endChoice();



//
//        // Odesłanie odpowiedzi
//        from("direct:notification").routeId("notification")
//                .log("fired notification")
//                .unmarshal().json(JsonLibrary.Jackson, BookingInfo.class)
//                .process((exchange) -> {
//                    exchange.getMessage().setHeader("work", "work2");
//
//                    BookingInfo bookingInfo = exchange.getMessage().getBody(BookingInfo.class);
//                    exchange.getMessage().setBody( bookingInfo);
//
//                })
//                .removeHeaders("Camel*")
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
////                .convertBodyTo(String.class);
    }

//
    private void car(){
//        final JaxbDataFormat jaxbCar = new JaxbDataFormat(Car.class.getPackage().getName()); // to jest zwracany typ
//
//        from("kafka:CarTravelReqTopic?brokers=localhost:9092").routeId("CarTravelReqTopic")
//                .log("CarTravelReqTopic fired")
//                .to("direct:CarTravelReq")
//                .end();
//
//
//        from("direct:CarTravelReq").routeId("CarTravelReq")
//                .log("fired CarTravelReq")
//                .unmarshal().json(JsonLibrary.Jackson, BookTravelRequest.class)
//                .process((exchange) ->
//                {
//                    String bookingTravelId = exchange.getMessage().getHeader("bookingTravelId", String.class);
//                    BookTravelRequest bookTravelRequest = exchange.getMessage().getBody(BookTravelRequest.class);
//                    exchange.getMessage().setBody(
//                            new CarBooking(
//                                    bookingTravelId,
//                                    bookTravelRequest.getTransportHub(),
//                                    bookTravelRequest.getTravelByCarTo()
//                                    )
//                            );
//                })
////                .marshal(jaxbCar)
////                .to("spring-ws:http://localhost:8081/soap-api/service/car")
//                .convertBodyTo(String.class)
//                .to("stream:out")
//                .convertBodyTo(String.class);
////                .unmarshal(jaxbCar);
//
//
////                .unmarshal(jaxbCar);
////                .process((exchange) -> {
////                    org.bp.flight.BookFligthResponse bookFlightResponse = exchange.getMessage().getBody(org.bp.flight.BookFligthResponse.class);
////                    String travelBookingId = exchange.getProperty("travelBookingId", String.class);
////                    bookingIdentifierService.assignFlightBookingId(travelBookingId, bookFlightResponse.getReturn().getId());
////                });

    }
}
