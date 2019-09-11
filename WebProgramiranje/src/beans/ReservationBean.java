package beans;

import models.Flight;
import models.Reservation;

public class ReservationBean {
	private String flightNumber;
	private String startDestination;
	private String arrivalDestination;
	private String planeModel;
	private long flightDate;
	private String id;
	private String user;
	private int numberOfPassengers;
	private int seatClass;
	private long date;

	public ReservationBean() {
	}

	public ReservationBean(Flight f, Reservation r) {
		this.flightNumber = f.getNumber();
		this.startDestination = f.getStartingDestination().getAirport() + ", "
				+ f.getStartingDestination().getCountry();
		this.arrivalDestination = f.getArrivalDestination().getAirport() + ", "
				+ f.getArrivalDestination().getCountry();
		this.planeModel = f.getPlaneModel();
		this.flightDate = f.getDate().getTime();
		this.id = r.getId();
		this.user = r.getUser().getUsername();
		this.numberOfPassengers = r.getNumberOfPassengers();
		this.seatClass = r.getSeatClass().ordinal();
		this.date = r.getDate().getTime();
	}

	public ReservationBean(String flightNumber, String startDestination, String arrivalDestination, String planeModel,
			long flightDate, String id, String user, int numberOfPassengers, int seatClass, long date) {
		this.flightNumber = flightNumber;
		this.startDestination = startDestination;
		this.arrivalDestination = arrivalDestination;
		this.planeModel = planeModel;
		this.flightDate = flightDate;
		this.id = id;
		this.user = user;
		this.numberOfPassengers = numberOfPassengers;
		this.seatClass = seatClass;
		this.date = date;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getStartDestination() {
		return startDestination;
	}

	public void setStartDestination(String startDestination) {
		this.startDestination = startDestination;
	}

	public String getArrivalDestination() {
		return arrivalDestination;
	}

	public void setArrivalDestination(String arrivalDestination) {
		this.arrivalDestination = arrivalDestination;
	}

	public String getPlaneModel() {
		return planeModel;
	}

	public void setPlaneModel(String planeModel) {
		this.planeModel = planeModel;
	}

	public long getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(long flightDate) {
		this.flightDate = flightDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

}
