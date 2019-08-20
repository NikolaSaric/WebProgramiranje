package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.DestinationActivityBean;
import models.Admin;
import models.Destination;
import models.User;

@Path("destination")
public class DestinationService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@POST
	@Path("/addDestination")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addDestination(Destination dest) {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof Admin)) {
			return null;
		}

		if (dest.getName() == null || dest.getName().equals("".trim())) {
			return "Enter destination name.";
		}
		if (dest.getCountry() == null || dest.getCountry().equals("".trim())) {
			return "Enter destination country.";
		}
		if (dest.getAirport() == null || dest.getAirport().equals("".trim())) {
			return "Enter destination airport.";
		}
		if (dest.getCoordinates() == null || dest.getCoordinates().equals("".trim())) {
			return "Enter destination coordinates.";
		}
		if (dest.getPicture() == null || dest.getPicture().equals("".trim())) {
			return "Enter destination picture.";
		}
		if (dest.getAirportCode() == null || dest.getAirportCode().equals("".trim())) {
			return "Enter destination airport code.";
		}

		@SuppressWarnings("unchecked")
		ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");
		if (destinations == null) {
			destinations = Util.loadDestinations();
			ctx.setAttribute("destinations", destinations);
		}

		for (Destination d : destinations) {
			if (d.getName().equals(dest.getName()) && d.getCountry().equals(dest.getCountry())) {
				return "Destination with given name already exists";
			}
		}

		dest.setActive(true);

		destinations.add(dest);

		String newDestinationLine = dest.getName() + "|" + dest.getCountry() + "|" + dest.getAirport() + "|"
				+ dest.getAirportCode() + "|" + dest.getCoordinates() + "|" + dest.getPicture() + "|" + "1";

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(Util.destinationsPath, true) // Set true for append mode
			);
			writer.newLine(); // Add new line
			writer.write(newDestinationLine);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Successfully added destination: " + dest.getName();
	}

	@GET
	@Path("/getAllDestinations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Destination> getAllDestinations() {

		@SuppressWarnings("unchecked")
		ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");

		if (destinations == null) {
			destinations = Util.loadDestinations();
			ctx.setAttribute("destinations", destinations);
		}

		return destinations;
	}

	@POST
	@Path("/deactivateDestination")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deactivateDestination(DestinationActivityBean dab) throws IOException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof Admin)) {
			return false;
		}

		@SuppressWarnings("unchecked")
		ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");

		for (Destination dest : destinations) {
			if (dest.getName().equals(dab.getName()) && dest.getCountry().equals(dab.getCountry())) {
				dest.setActive(false);
				Util.saveDestinations(destinations);
				return true;
			}
		}

		return false;
	}

	@POST
	@Path("/activateDestination")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean activateDestination(DestinationActivityBean dab) throws IOException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof Admin)) {
			return false;
		}

		@SuppressWarnings("unchecked")
		ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");

		for (Destination dest : destinations) {
			if (dest.getName().equals(dab.getName()) && dest.getCountry().equals(dab.getCountry())) {
				dest.setActive(true);
				Util.saveDestinations(destinations);
				return true;
			}
		}

		return false;
	}

	@POST
	@Path("/editDestination")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean editDestination(Destination dest) throws IOException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof Admin)) {
			return false;
		}

		if (dest.getName() == null || dest.getName().equals("".trim())) {
			return false;
		}
		if (dest.getCountry() == null || dest.getCountry().equals("".trim())) {
			return false;
		}
		if (dest.getAirport() == null || dest.getAirport().equals("".trim())) {
			return false;
		}
		if (dest.getAirportCode() == null || dest.getAirportCode().equals("".trim())) {
			return false;
		}
		if (dest.getCoordinates() == null || dest.getCoordinates().equals("".trim())) {
			return false;
		}
		if (dest.getPicture() == null || dest.getPicture().equals("".trim())) {
			return false;
		}

		@SuppressWarnings("unchecked")
		ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");

		for (Destination d : destinations) {
			if (d.getName().equals(dest.getName()) && d.getCountry().equals(dest.getCountry())) {
				d.setName(dest.getName());
				d.setCountry(dest.getCountry());
				d.setAirport(dest.getAirport());
				d.setAirportCode(dest.getAirportCode());
				d.setCoordinates(dest.getCoordinates());
				d.setPicture(dest.getPicture());

				Util.saveDestinations(destinations);
				return true;
			}
		}

		return false;
	}
}
