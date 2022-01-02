package org.bp.train;

import org.bp.train.exceptions.TrainBookingException;
import org.bp.train.model.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@org.springframework.web.bind.annotation.RestController


@OpenAPIDefinition(info = @Info(
		title = "Train ticket booking service",
		version = "1",
		description = "Service for booking train ticket"))

public class TrainBookingManagerService {

	private TicketManager ticketManager = new TicketManager();


	@org.springframework.web.bind.annotation.PostMapping("/trainBooking")
	@Operation(
			summary = "train booking operation",
			description = "operation for train booking",
			responses = {
					@ApiResponse(responseCode = "200",
							description = "OK",
							content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookingInfoResponse.class))}),
					@ApiResponse(responseCode = "400", description = "Bad Request",
							content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TrainBookingException.class))})
			})
	public BookingInfoResponse trainBooking(
			@org.springframework.web.bind.annotation.RequestBody BookTravelRequest bookTravelRequest
	) {
		TicketReservation ticketReservation = ticketManager.bookTrainTicket(
				bookTravelRequest.getId(),
				new TrainTicketRequest(
						bookTravelRequest.getTravelByTrainForm(),
						bookTravelRequest.getTravelByCarTo(),
						bookTravelRequest.getNumberOfPerson())
		);

		BookingInfoResponse bookingInfoResponse = new BookingInfoResponse();
		bookingInfoResponse.setId(ticketReservation.getReservationId());
		bookingInfoResponse.setTravelTimeByTrain(400);

		return bookingInfoResponse;
    }
}
