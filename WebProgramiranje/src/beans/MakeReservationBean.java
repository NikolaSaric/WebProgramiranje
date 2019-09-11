package beans;

public class MakeReservationBean {
	private String flightNumber;
	private int numberOfPassengers;
	private int seatClass;

	public MakeReservationBean() {
	}

	public MakeReservationBean(String flightNumber, int numberOfPassengers, int seatClass) {
		this.flightNumber = flightNumber;
		this.numberOfPassengers = numberOfPassengers;
		this.seatClass = seatClass;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

	public int getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(int seatClass) {
		this.seatClass = seatClass;
	}

}
