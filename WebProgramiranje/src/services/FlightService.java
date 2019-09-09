package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.FlightBean;
import beans.NewFlightBean;
import beans.SearchFlightsBean;
import models.Admin;
import models.Destination;
import models.Flight;
import models.FlightClass;
import models.Reservation;
import models.User;

@Path("flight")
public class FlightService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@POST
	@Path("/addFlight")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addFlight(NewFlightBean nfb) throws ParseException, IOException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof Admin)) {
			return "No logged in admin.";
		}
		System.out.println(nfb.getTime());
		if (nfb.getNumber() == null || nfb.getNumber().equals("".trim())) {
			return "Enter flight number.";
		}
		if (nfb.getStartDestinationName() == null || nfb.getStartDestinationName().equals("".trim())) {
			return "Enter starting flight destination name.";

		}
		if (nfb.getStartDestinationCountry() == null || nfb.getStartDestinationCountry().equals("".trim())) {
			return "Enter starting flight destination country.";
		}
		if (nfb.getArrivalDestinationName() == null || nfb.getArrivalDestinationName().equals("".trim())) {
			return "Enter arrival flight destination name.";
		}
		if (nfb.getArrivalDestinationCountry() == null || nfb.getArrivalDestinationCountry().equals("".trim())) {
			return "Enter arrival flight destination country.";
		}
		if (nfb.getPlaneModel() == null || nfb.getPlaneModel().equals("".trim())) {
			return "Enter plane model.";
		}
		if (nfb.getDate() == null || nfb.getDate().equals("".trim())) {
			return "Enter flight date.";
		}
		if (nfb.getTime() == null || nfb.getTime().equals("".trim())) {
			return "Enter flight time.";
		}

		if (nfb.getStartDestinationCountry().equals(nfb.getArrivalDestinationCountry())
				&& nfb.getStartDestinationName().equals(nfb.getArrivalDestinationName())) {
			return "Starting and arrival destinations must be different.";
		}

		@SuppressWarnings("unchecked")
		ArrayList<Flight> flights = (ArrayList<Flight>) ctx.getAttribute("flights");
		@SuppressWarnings("unchecked")
		ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");

		if (flights == null) {
			flights = Util.loadFlights(destinations);
			ctx.setAttribute("flights", flights);
		}
		for (Flight f : flights) {
			if (f.getNumber().equals(nfb.getNumber())) {
				return "Flight with given number already exists.";
			}
		}

		Destination startDestination = null;
		Destination arrivalDestination = null;

		for (Destination dest : destinations) {
			if (startDestination != null && arrivalDestination != null) {
				break;
			}
			if (dest.getName().equals(nfb.getStartDestinationName())
					&& dest.getCountry().equals(nfb.getStartDestinationCountry())) {
				startDestination = dest;
			} else if (dest.getName().equals(nfb.getArrivalDestinationName())
					&& dest.getCountry().equals(nfb.getArrivalDestinationName())) {
				arrivalDestination = dest;
			}
		}

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date flightDate = dateformat.parse(nfb.getDate() + " " + nfb.getTime() + ":00");

		Flight newFlight = new Flight();
		newFlight.setNumber(nfb.getNumber());
		newFlight.setStartingDestination(startDestination);
		newFlight.setArrivalDestination(arrivalDestination);
		newFlight.setReservations(new ArrayList<Reservation>());
		newFlight.setPrice(nfb.getPrice());
		newFlight.setPlaneModel(nfb.getPlaneModel());
		newFlight.setFirstClass(nfb.getFirstClass());
		newFlight.setBusinessClass(nfb.getBusinessClass());
		newFlight.setEcoClass(nfb.getEcoClass());
		newFlight.setDate(flightDate);
		newFlight.setFlightClass(FlightClass.values()[nfb.getFlightClass()]);

		flights.add(newFlight);

		String newFlightLine = nfb.getNumber() + "|" + nfb.getStartDestinationName() + "|"
				+ nfb.getStartDestinationCountry() + "|" + nfb.getArrivalDestinationName() + "|"
				+ nfb.getArrivalDestinationCountry() + "| |" + nfb.getPrice() + "|" + nfb.getPlaneModel() + "|"
				+ nfb.getFirstClass() + "|" + nfb.getBusinessClass() + "|" + nfb.getEcoClass() + "|" + nfb.getDate()
				+ " " + nfb.getTime() + ":00" + "|" + nfb.getFlightClass();

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(Util.flightsPath, true) // Set true for append mode
			);
			writer.newLine(); // Add new line
			writer.write(newFlightLine);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "true";

	}

	@GET
	@Path("/getAllFlights")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<FlightBean> getAllFlights() throws ParseException {
		@SuppressWarnings("unchecked")
		ArrayList<Flight> flights = (ArrayList<Flight>) ctx.getAttribute("flights");
		@SuppressWarnings("unchecked")
		ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");

		if (flights == null) {
			flights = Util.loadFlights(destinations);
			ctx.setAttribute("flights", flights);
		}

		ArrayList<FlightBean> flightBeans = new ArrayList<FlightBean>();

		for (Flight f : flights) {
			FlightBean fb = new FlightBean(f);
			flightBeans.add(fb);
		}

		return flightBeans;
	}

	@DELETE
	@Path("/deleteFlight/{flightNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteFlight(@PathParam("flightNumber") String flightNumber) throws ParseException, IOException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof Admin)) {
			return "No admin logged in.";
		}
		System.out.println(flightNumber);
		if (flightNumber == null || flightNumber.equals("".trim())) {
			return "There was an error with deleting flight.";
		}

		@SuppressWarnings("unchecked")
		ArrayList<Flight> flights = (ArrayList<Flight>) ctx.getAttribute("flights");
		@SuppressWarnings("unchecked")
		ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");

		if (flights == null) {
			flights = Util.loadFlights(destinations);
			ctx.setAttribute("flights", flights);
		}

		Flight foundFlight = null;

		for (Flight f : flights) {
			if (f.getNumber().equals(flightNumber)) {
				if (f.getReservations().size() != 0) {
					return "You can't delete this flight, it has reservations.";
				}
				foundFlight = f;
				break;
			}
		}
		if (foundFlight == null) {
			return "No flights found.";
		}
		flights.remove(foundFlight);

		Util.saveFlights(flights);

		return "true";

	}

	@POST
	@Path("/searchFlights")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<FlightBean> searchFlights(SearchFlightsBean sfb) throws ParseException {

		System.out.println("Start country: " + sfb.getStartCountry());
		System.out.println("Start airport: " + sfb.getStartAirport());
		System.out.println("Arrival country: " + sfb.getArrivalCountry());
		System.out.println("Arrival airport: " + sfb.getArrivalAirport());
		System.out.println("Date: " + sfb.getDate());

		@SuppressWarnings("unchecked")
		ArrayList<Flight> flights = (ArrayList<Flight>) ctx.getAttribute("flights");
		@SuppressWarnings("unchecked")
		ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");

		if (flights == null) {
			flights = Util.loadFlights(destinations);
			ctx.setAttribute("flights", flights);
		}
		ArrayList<FlightBean> flightBeans = new ArrayList<FlightBean>();

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date flightDate = dateformat.parse(sfb.getDate());

		for (Flight f : flights) {
			if ((sfb.getStartCountry().equals("")
					|| sfb.getStartCountry().equals(f.getStartingDestination().getCountry()))
					&& (sfb.getStartAirport().equals("")
							|| sfb.getStartAirport().equals(f.getStartingDestination().getName()))
					&& (sfb.getArrivalCountry().equals("")
							|| sfb.getArrivalCountry().equals(f.getArrivalDestination().getCountry()))
					&& (sfb.getArrivalAirport().equals("")
							|| sfb.getArrivalAirport().equals(f.getArrivalDestination().getName()))
					&& f.getDate().after(flightDate)) {

				FlightBean fb = new FlightBean(f);
				flightBeans.add(fb);
			}
		}

		return flightBeans;
	}
}
