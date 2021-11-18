package org.bp.train;

import org.bp.train.exceptions.TrainBookingException;
import org.bp.train.model.TrainSeat;
import org.bp.train.model.TrainTicket;
import org.bp.train.model.TrainTicketRequest;
import org.bp.train.model.TrainTicketResponse;

import java.util.Arrays;
import java.util.List;

public class TicketManager {
    private List<TrainSeat> trainSeats = Arrays.asList(
            new TrainSeat(1,1), new TrainSeat(1,2), new TrainSeat(1,3), new TrainSeat(1,4),
            new TrainSeat(2,1), new TrainSeat(2,2), new TrainSeat(2,3), new TrainSeat(2,4),
            new TrainSeat(3,1), new TrainSeat(3,2), new TrainSeat(3,3), new TrainSeat(3,4)
    );

    public TrainTicketResponse bookTrainTicket(TrainTicketRequest trainTicketRequest) {
        if (!trainTicketRequest.isCorrect() )
            throw new TrainBookingException("Niepoprawnie uzupełnione dane");

        TrainTicketResponse trainTicketResponse = new TrainTicketResponse();

        for (int i = 0; i < trainTicketRequest.getNumberOfPerson(); i++)
            trainTicketResponse.bookedTicket.add(new TrainTicket(getFreeTrainSeats(), trainTicketRequest.getFrom(), trainTicketRequest.getTo()));

        return trainTicketResponse;
    }

    private TrainSeat getFreeTrainSeats() {
        TrainSeat trainSeat = trainSeats
                .stream()
                .filter(t -> t.isBooked() == false)
                .findFirst()
                .orElse(null);
        if (trainSeat == null)
            throw new TrainBookingException("Brak miejsc");

        trainSeat.setBooked(true);
        return trainSeat;
    }

}
