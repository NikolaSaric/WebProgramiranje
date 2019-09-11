package models;

import java.util.Date;

public class Reservation {
	private String id;
	private RegularUser user;
	private Date date;
	private SeatClass seatClass;
	private int numberOfPassengers;

	public Reservation(String id, RegularUser user, Date date, SeatClass seatClass, int numberOfPassengers) {
		this.id = id;
		this.user = user;
		this.date = date;
		this.seatClass = seatClass;
		this.numberOfPassengers = numberOfPassengers;
	}

	public Reservation() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RegularUser getUser() {
		return user;
	}

	public void setUser(RegularUser user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

}
