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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.FlightBean;
import beans.NewFlightBean;
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
	public String addFlight(NewFlightBean nfb) throws ParseException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof Admin)) {
			return "No logged in admin.";
		}

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

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		Date flightDate = dateformat.parse(nfb.getDate());

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
				+ "|" + nfb.getFlightClass();

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
}
