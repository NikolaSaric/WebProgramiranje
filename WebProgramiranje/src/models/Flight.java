package models;

import java.util.ArrayList;
import java.util.Date;

public class Flight {
	private String number;
	private Destination startingDestination;
	private Destination arrivalDestination;
	private ArrayList<Reservation> reservations;
	private float price;
	private String planeModel;
	private int firstClass;
	private int businessClass;
	private int ecoClass;
	private Date date;
	private FlightClass flightClass;

	public Flight(String number, Destination startingDestination, Destination arrivalDestination,
			ArrayList<Reservation> reservations, float price, String planeModel, int firstClass, int businessClass,
			int ecoClass, Date date, FlightClass flightClass) {
		this.number = number;
		this.startingDestination = startingDestination;
		this.arrivalDestination = arrivalDestination;
		this.reservations = reservations;
		this.price = price;
		this.planeModel = planeModel;
		this.firstClass = firstClass;
		this.businessClass = businessClass;
		this.ecoClass = ecoClass;
		this.date = date;
		this.flightClass = flightClass;
	}

	public Flight() {
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Destination getStartingDestination() {
		return startingDestination;
	}

	public void setStartingDestination(Destination startingDestination) {
		this.startingDestination = startingDestination;
	}

	public Destination getArrivalDestination() {
		return arrivalDestination;
	}

	public void setArrivalDestination(Destination arrivalDestination) {
		this.arrivalDestination = arrivalDestination;
	}

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getPlaneModel() {
		return planeModel;
	}

	public void setPlaneModel(String planeModel) {
		this.planeModel = planeModel;
	}

	public int getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(int firstClass) {
		this.firstClass = firstClass;
	}

	public int getBusinessClass() {
		return businessClass;
	}

	public void setBusinessClass(int businessClass) {
		this.businessClass = businessClass;
	}

	public int getEcoClass() {
		return ecoClass;
	}

	public void setEcoClass(int ecoClass) {
		this.ecoClass = ecoClass;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public FlightClass getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(FlightClass flightClass) {
		this.flightClass = flightClass;
	}

}
