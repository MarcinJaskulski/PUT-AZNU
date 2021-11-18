package org.bp.train.model;

public class TrainTicketRequest {

	private Point from;
	private Point to;
	private	int numberOfPerson;

	public TrainTicketRequest(Point from, Point to, int numberOfPerson) {
		this.from = from;
		this.to = to;
		this.numberOfPerson = numberOfPerson;
	}

	public int getNumberOfPerson() {
		return numberOfPerson;
	}
	public void setNumberOfPerson(int numberOfperson) {
		this.numberOfPerson = numberOfperson;
	}

	public Point getTo() { return to; }
	public void setTo(Point to) {
		this.to = to;
	}

	public Point getFrom() {
		return from;
	}
	public void setFrom(Point from) {
		this.from = from;
	}

	public boolean isCorrect() {
		if(this.getNumberOfPerson() > 0 &&
				this.getFrom().isCorrect() &&
				this.getTo().isCorrect())
			return true;
		return false;
	}
}
