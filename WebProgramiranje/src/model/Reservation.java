package model;

import java.util.Date;

public class Reservation {
	private int id;
	private RegularUser user;
	private Date date;
	private SeatClass seatClass;
	private int numberOfPassengers;

	public Reservation(int id, RegularUser user, Date date, SeatClass seatClass, int numberOfPassengers) {
		this.id = id;
		this.user = user;
		this.date = date;
		this.seatClass = seatClass;
		this.numberOfPassengers = numberOfPassengers;
	}

	public Reservation() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
