package model;

public class Destination {
	private String name;
	private String country;
	private String airport;
	private String airportCode;
	private String coordinates;
	private String picture;
	private boolean state;

	public Destination(String name, String country, String airport, String airportCode, String coordinates,
			String picture, boolean state) {
		this.name = name;
		this.country = country;
		this.airport = airport;
		this.airportCode = airportCode;
		this.coordinates = coordinates;
		this.picture = picture;
		this.state = state;
	}

	public Destination() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
