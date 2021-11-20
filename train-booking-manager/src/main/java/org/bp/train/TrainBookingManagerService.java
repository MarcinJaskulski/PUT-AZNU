package org.bp.train;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bp.train.model.*;
import org.springframework.stereotype.Component;


@Component
public class TrainBookingManagerService extends RouteBuilder {

	private TicketManager ticketManager = new TicketManager();

    @Override
    public void configure() throws Exception {
        trainBooking();
    }

    private void trainBooking() {
		from("kafka:TravelReqTopic?brokers=localhost:9092").routeId("bookTrain")
				.log("fired bookTrain")
				.unmarshal().json(JsonLibrary.Jackson, BookTravelRequest.class)
				.process(
						(exchange) -> {
							String bookingTravelId = exchange.getMessage().getHeader("bookingTravelId", String.class);
							BookTravelRequest bookTravelRequest = exchange.getMessage().getBody(BookTravelRequest.class);

							TicketReservation ticketReservation = ticketManager.bookTrainTicket(
									bookingTravelId,
									new TrainTicketRequest(
										bookTravelRequest.getTravelByTrainForm(),
										bookTravelRequest.getTravelByCarTo(),
										bookTravelRequest.getNumberOfPerson())
									);

							BookingInfo bookingInfo = new BookingInfo();
							bookingInfo.setId(bookingTravelId);
							bookingInfo.setTravelTimeByTrain(ticketReservation.getTravelTimeByTrain());
							exchange.getMessage().setBody(bookingInfo);
						}
				)
				.marshal().json()
				.setHeader("serviceType", constant("train"))
				.to("stream:out")
				.to("kafka:bookResponseTopic?brokers=localhost:9092");
    }
}
