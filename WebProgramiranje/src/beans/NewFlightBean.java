package beans;

public class NewFlightBean {
	private String number;
	private String startDestinationName;
	private String startDestinationCountry;
	private String arrivalDestinationName;
	private String arrivalDestinationCountry;
	private float price;
	private String planeModel;
	private int firstClass;
	private int businessClass;
	private int ecoClass;
	private String date;
	private String time;
	private int flightClass;

	public NewFlightBean() {
	}

	public NewFlightBean(String number, String startDestinationName, String startDestinationCountry,
			String arrivalDestinationName, String arrivalDestinationCountry, float price, String planeModel,
			int firstClass, int businessClass, int ecoClass, String date, String time, int flightClass) {
		this.number = number;
		this.startDestinationName = startDestinationName;
		this.startDestinationCountry = startDestinationCountry;
		this.arrivalDestinationName = arrivalDestinationName;
		this.arrivalDestinationCountry = arrivalDestinationCountry;
		this.price = price;
		this.planeModel = planeModel;
		this.firstClass = firstClass;
		this.businessClass = businessClass;
		this.ecoClass = ecoClass;
		this.date = date;
		this.time = time;
		this.flightClass = flightClass;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStartDestinationName() {
		return startDestinationName;
	}

	public void setStartDestinationName(String startDestinationName) {
		this.startDestinationName = startDestinationName;
	}

	public String getStartDestinationCountry() {
		return startDestinationCountry;
	}

	public void setStartDestinationCountry(String startDestinationCountry) {
		this.startDestinationCountry = startDestinationCountry;
	}

	public String getArrivalDestinationName() {
		return arrivalDestinationName;
	}

	public void setArrivalDestinationName(String arrivalDestinationName) {
		this.arrivalDestinationName = arrivalDestinationName;
	}

	public String getArrivalDestinationCountry() {
		return arrivalDestinationCountry;
	}

	public void setArrivalDestinationCountry(String arrivalDestinationCountry) {
		this.arrivalDestinationCountry = arrivalDestinationCountry;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(int flightClass) {
		this.flightClass = flightClass;
	}

}
