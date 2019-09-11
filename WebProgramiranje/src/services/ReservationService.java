package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

import beans.MakeReservationBean;
import beans.ReservationBean;
import models.Destination;
import models.Flight;
import models.RegularUser;
import models.Reservation;
import models.SeatClass;
import models.User;

@Path("reservation")
public class ReservationService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@SuppressWarnings("unchecked")
	@POST
	@Path("/makeReservation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ReservationBean makeReservation(MakeReservationBean mrb) throws ParseException, IOException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof RegularUser)) {
			System.out.println("Puko 1");
			return null;
		}

		if (loggedUser.isBlocked()) {
			return null;
		}

		if (mrb.getFlightNumber() == null || mrb.getFlightNumber().equals("".trim())) {
			System.out.println("Puko 2");
			return null;
		}
		if (mrb.getNumberOfPassengers() < 1) {
			System.out.println("Puko 3");
			return null;
		}
		if (mrb.getSeatClass() < 0 || mrb.getSeatClass() > 2) {
			System.out.println("Puko 4");
			return null;
		}

		ArrayList<Flight> flights = (ArrayList<Flight>) ctx.getAttribute("flights");

		// Checks if flight with given number exists. (All flight numbers are unique!)
		if (flights == null) {
			ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");
			ArrayList<User> users = (ArrayList<User>) ctx.getAttribute("users");
			ArrayList<Reservation> reservations = (ArrayList<Reservation>) ctx.getAttribute("reservations");
			if (reservations == null) {
				reservations = Util.loadReservations(users);
				ctx.setAttribute("reservations", reservations);
			}
			flights = Util.loadFlights(destinations, reservations);
			ctx.setAttribute("flights", flights);
		}
		Flight flight = null;
		for (Flight f : flights) {
			if (f.getNumber().equals(mrb.getFlightNumber())) {
				flight = f;
				break;
			}
		}
		if (flight == null) {
			System.out.println("Puko 5");
			return null;
		}

		// Checks if there are enough free seats in wanted class.
		switch (mrb.getSeatClass()) {
		case 0:
			if (flight.getFirstClass() - mrb.getNumberOfPassengers() < 0) {
				System.out.println("Puko 6");
				return null;
			}
			flight.setFirstClass(flight.getFirstClass() - mrb.getNumberOfPassengers());
			break;
		case 1:
			if (flight.getBusinessClass() - mrb.getNumberOfPassengers() < 0) {
				System.out.println("Puko 7");
				return null;
			}
			flight.setBusinessClass(flight.getBusinessClass() - mrb.getNumberOfPassengers());
			break;
		case 2:
			if (flight.getEcoClass() - mrb.getNumberOfPassengers() < 0) {
				System.out.println("Puko 8");
				return null;
			}
			flight.setEcoClass(flight.getEcoClass() - mrb.getNumberOfPassengers());
			break;
		}

		Util.saveFlights(flights);

		Reservation reservation = new Reservation();
		reservation.setDate(new Date());
		reservation.setUser((RegularUser) loggedUser);
		reservation.setNumberOfPassengers(mrb.getNumberOfPassengers());
		reservation.setSeatClass(SeatClass.values()[mrb.getSeatClass()]);

		// Gets hash map with all flights and their reservation numbers.
		HashMap<String, Integer> reservationID = (HashMap<String, Integer>) ctx.getAttribute("reservationID");
		if (reservationID == null) {
			reservationID = Util.loadReservationID();
			ctx.setAttribute("reservationID", reservationID);
		}

		// If there is no flight with given number in hash map, add new one and save it.
		Integer reservationNumber = reservationID.get(mrb.getFlightNumber());
		if (reservationNumber == null) {
			reservationID.put(mrb.getFlightNumber(), 1);

			BufferedWriter writer;
			writer = new BufferedWriter(new FileWriter(Util.reservationIDPath, true) // Set true for append mode
			);
			String resLine = mrb.getFlightNumber() + "|" + 1;

			try {
				writer.write(resLine);
				writer.newLine(); // Add new line

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			writer.close();
			reservation.setId(mrb.getFlightNumber() + " " + 1);
		} else {
			reservation.setId(mrb.getFlightNumber() + " " + (reservationNumber + 1));
			reservationID.put(mrb.getFlightNumber(), reservationNumber + 1);
			Util.saveReservationID(reservationID);
		}

		ArrayList<Reservation> reservations = (ArrayList<Reservation>) ctx.getAttribute("reservations");
		if (reservations == null) {
			ArrayList<User> users = (ArrayList<User>) ctx.getAttribute("users");
			reservations = Util.loadReservations(users);
			ctx.setAttribute("reservations", reservations);
		}

		reservations.add(reservation);
		flight.getReservations().add(reservation);

		BufferedWriter writer2;
		writer2 = new BufferedWriter(new FileWriter(Util.reservationsPath, true) // Set true for append mode
		);
		String reservationLine = reservation.getId() + "|" + reservation.getUser().getUsername() + "|"
				+ reservation.getDate().getTime() + "|" + reservation.getSeatClass().ordinal() + "|"
				+ reservation.getNumberOfPassengers();

		try {
			writer2.write(reservationLine);
			writer2.newLine(); // Add new line

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writer2.close();

		System.out.println("Izvrsio se!");
		return new ReservationBean(flight, reservation);
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getReservations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ReservationBean> getReservations() throws ParseException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof RegularUser)) {
			System.out.println("Puko 1");
			return null;
		}

		ArrayList<ReservationBean> reservationBeans = new ArrayList<ReservationBean>();

		ArrayList<Flight> flights = (ArrayList<Flight>) ctx.getAttribute("flights");

		// Checks if flight with given number exists. (All flight numbers are unique!)
		if (flights == null) {
			ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");
			ArrayList<User> users = (ArrayList<User>) ctx.getAttribute("users");
			ArrayList<Reservation> reservations = (ArrayList<Reservation>) ctx.getAttribute("reservations");
			if (reservations == null) {
				reservations = Util.loadReservations(users);
				ctx.setAttribute("reservations", reservations);
			}
			flights = Util.loadFlights(destinations, reservations);
			ctx.setAttribute("flights", flights);
		}

		for (Flight f : flights) {
			for (Reservation r : f.getReservations()) {
				if (r.getUser().getUsername().equals(loggedUser.getUsername())) {
					reservationBeans.add(new ReservationBean(f, r));
				}
			}
		}

		return reservationBeans;

	}

	@SuppressWarnings("unchecked")
	@DELETE
	@Path("/deleteReservation/{reservationID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteReservation(@PathParam("reservationID") String reservationID)
			throws ParseException, IOException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof RegularUser)) {
			return "No user logged in.";
		}

		String flightNumber = reservationID.split(" ")[0];

		ArrayList<Flight> flights = (ArrayList<Flight>) ctx.getAttribute("flights");
		ArrayList<Reservation> reservations = (ArrayList<Reservation>) ctx.getAttribute("reservations");
		// Checks if flight with given number exists. (All flight numbers are unique!)
		if (flights == null) {
			ArrayList<Destination> destinations = (ArrayList<Destination>) ctx.getAttribute("destinations");
			ArrayList<User> users = (ArrayList<User>) ctx.getAttribute("users");
			if (reservations == null) {
				reservations = Util.loadReservations(users);
				ctx.setAttribute("reservations", reservations);
			}
			flights = Util.loadFlights(destinations, reservations);
			ctx.setAttribute("flights", flights);
		}

		for (Flight f : flights) {

			if (f.getNumber().equals(flightNumber)) {
				Date today = new Date();
				if (f.getDate().getTime() - today.getTime() < 0) {
					return "You can not delete past reservations.";
				}

				for (Reservation r : f.getReservations()) {
					if (r.getId().equals(reservationID)) {
						if (r.getSeatClass().ordinal() == 0) {
							f.setFirstClass(f.getFirstClass() + r.getNumberOfPassengers());
						} else if (r.getSeatClass().ordinal() == 1) {
							f.setBusinessClass(f.getBusinessClass() + r.getNumberOfPassengers());
						} else if (r.getSeatClass().ordinal() == 2) {
							f.setEcoClass(f.getEcoClass() + r.getNumberOfPassengers());
						}
						f.getReservations().remove(r);
						break;
					}
				}
			}
		}
		for (Reservation r : reservations) {
			if (r.getId().equals(reservationID)) {
				reservations.remove(r);
				break;
			}
		}

		Util.saveFlights(flights);
		Util.saveReservations(reservations);

		return "true";
	}
}
