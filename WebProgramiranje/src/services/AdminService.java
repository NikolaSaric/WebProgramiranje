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
import models.Destination;

@Path("admin")
public class AdminService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@POST
	@Path("/addDestination")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addDestination(Destination dest) {
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
		}

		return destinations;
	}

}
