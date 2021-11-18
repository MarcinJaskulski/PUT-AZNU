package org.bp.train.model;

public class TrainTicket {

	private TrainSeat trainSeat;
	private Point from;
	private Point to;
	
	
	public TrainTicket(TrainSeat trainSeat, Point from, Point to) {
		this.trainSeat = trainSeat;
		this.from = from;
		this.to = to;
	}
	
	public TrainSeat getTrainSeat() {
		return trainSeat;
	}
	public void setTrainSeat(TrainSeat trainSeat) {
		this.trainSeat = trainSeat;
	}
	public Point getFrom() {
		return from;
	}
	public void setFrom(Point from) {
		this.from = from;
	}
	public Point getTo() {
		return to;
	}
	public void setTo(Point to) {
		this.to = to;
	}
}
