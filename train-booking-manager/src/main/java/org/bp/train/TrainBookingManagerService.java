package org.bp.train;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bp.train.exceptions.TrainBookingException;
import org.bp.train.model.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


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

							TrainTicketResponse trainTicketResponse = ticketManager.bookTrainTicket(new TrainTicketRequest(
									bookTravelRequest.getTravelByTrainForm(),
									bookTravelRequest.getTravelByCarTo(),
									bookTravelRequest.getNumberOfPerson()));

							exchange.getMessage().setBody(trainTicketResponse);
						}
				)
				.marshal().json()
				.to("kafka:bookTrainResponseTopic?brokers=localhost:9092");
    }
}
