package org.bp.train;

import org.bp.train.model.TrainTicket;

import java.util.ArrayList;
import java.util.List;

public class TicketReservation {

    private String reservationId;
    private List<TrainTicket> trainTickets = new ArrayList<>();
    private Integer travelTimeByTrain = null;

    public TicketReservation() {
    }

    public TicketReservation(String reservationId, List<TrainTicket> trainTickets, Integer travelTimeByTrain) {
        this.reservationId = reservationId;
        this.trainTickets = trainTickets;
        this.travelTimeByTrain = travelTimeByTrain;
    }

    public String getReservationId() {
        return reservationId;
    }
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
    public List<TrainTicket> getTrainTicket() {
        return trainTickets;
    }
    public void setTrainTicket(List<TrainTicket> trainTickets) {
        this.trainTickets = trainTickets;
    }
    public Integer getTravelTimeByTrain() {
        return travelTimeByTrain;
    }
    public void setTravelTimeByTrain(Integer travelTimeByTrain) {
        this.travelTimeByTrain = travelTimeByTrain;
    }
}
