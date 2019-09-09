package beans;

public class SearchFlightsBean {
	private String startCountry;
	private String arrivalCountry;
	private String startAirport;
	private String arrivalAirport;
	private String date;

	public SearchFlightsBean() {
	}

	public SearchFlightsBean(String startCountry, String arrivalCountry, String startAirport, String arrivalAirport,
			String date) {
		this.startCountry = startCountry;
		this.arrivalCountry = arrivalCountry;
		this.startAirport = startAirport;
		this.arrivalAirport = arrivalAirport;
		this.date = date;
	}

	public String getStartCountry() {
		return startCountry;
	}

	public void setStartCountry(String startCountry) {
		this.startCountry = startCountry;
	}

	public String getArrivalCountry() {
		return arrivalCountry;
	}

	public void setArrivalCountry(String arrivalCountry) {
		this.arrivalCountry = arrivalCountry;
	}

	public String getStartAirport() {
		return startAirport;
	}

	public void setStartAirport(String startAirport) {
		this.startAirport = startAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
