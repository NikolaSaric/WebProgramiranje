package beans;

import models.Flight;
import models.FlightClass;

public class FlightBean {
	private String number;
	private String startDestination;
	private String arrivalDestination;
	private float price;
	private String planeModel;
	private int firstClass;
	private int businessClass;
	private int ecoClass;
	private String date;
	private int flightClass;
	private int soldTickets;

	public FlightBean() {
	}

	public FlightBean(String number, String startDestination, String arrivalDestination, float price, String planeModel,
			int firstClass, int businessClass, int ecoClass, String date, int flightClass, int soldTickets) {
		this.number = number;
		this.startDestination = startDestination;
		this.arrivalDestination = arrivalDestination;
		this.price = price;
		this.planeModel = planeModel;
		this.firstClass = firstClass;
		this.businessClass = businessClass;
		this.ecoClass = ecoClass;
		this.date = date;
		this.flightClass = flightClass;
		this.soldTickets = soldTickets;
	}

	public FlightBean(Flight f) {
		this.number = f.getNumber();
		this.startDestination = f.getStartingDestination().getName() + ", " + f.getStartingDestination().getCountry();
		this.arrivalDestination = f.getArrivalDestination().getName() + ", " + f.getArrivalDestination().getCountry();
		this.date = f.getDate().toString();
		this.firstClass = f.getFirstClass();
		this.businessClass = f.getBusinessClass();
		this.ecoClass = f.getEcoClass();
		this.flightClass = f.getFlightClass().ordinal();
		this.price = f.getPrice();
		this.planeModel = f.getPlaneModel();
		this.soldTickets = f.getReservations().size();

	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(int flightClass) {
		this.flightClass = flightClass;
	}

	public int getSoldTickets() {
		return soldTickets;
	}

	public void setSoldTickets(int soldTickets) {
		this.soldTickets = soldTickets;
	}

}
