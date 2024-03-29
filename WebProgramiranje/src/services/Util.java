package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import models.Admin;
import models.Destination;
import models.Flight;
import models.FlightClass;
import models.RegularUser;
import models.Reservation;
import models.SeatClass;
import models.User;

public class Util {
	final static String usersPath = "C:\\Users\\NikolaS\\Documents\\FAKS\\WEB\\Web Projekat\\WebProgramiranje\\WebProgramiranje\\src\\resources\\users.txt";
	final static String destinationsPath = "C:\\Users\\NikolaS\\Documents\\FAKS\\WEB\\Web Projekat\\WebProgramiranje\\WebProgramiranje\\src\\resources\\destinations.txt";
	final static String flightsPath = "C:\\Users\\NikolaS\\Documents\\FAKS\\WEB\\Web Projekat\\WebProgramiranje\\WebProgramiranje\\src\\resources\\flights.txt";
	final static String reservationIDPath = "C:\\Users\\NikolaS\\Documents\\FAKS\\WEB\\Web Projekat\\WebProgramiranje\\WebProgramiranje\\src\\resources\\reservationIDs.txt";
	final static String reservationsPath = "C:\\Users\\NikolaS\\Documents\\FAKS\\WEB\\Web Projekat\\WebProgramiranje\\WebProgramiranje\\src\\resources\\reservations.txt";

	public static ArrayList<User> loadUsers() {
		ArrayList<User> users = new ArrayList<User>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(usersPath));
			String line = reader.readLine();
			while (line != null) {
				String[] l = line.split("\\|");
				boolean blocked;

				if (l[7].equals("0")) {
					blocked = false;
				} else {
					blocked = true;
				}
				if (l[8].equals("admin")) {
					Admin admin = new Admin(l[0], l[1], l[2], l[3], l[4], l[5], l[6], blocked);
					users.add(admin);
				} else if (l[8].equals("regular")) {
					RegularUser regularUser = new RegularUser(l[0], l[1], l[2], l[3], l[4], l[5], l[6], blocked);
					users.add(regularUser);
				}
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return users;
	}

	public static void saveUsers(ArrayList<User> users) throws IOException {
		BufferedWriter writer;
		writer = new BufferedWriter(new FileWriter(Util.usersPath, false) // Set true for append mode
		);
		int c = 1;
		for (User ub : users) {
			String newUserLine = ub.getUsername() + "|" + ub.getPassword() + "|" + ub.getFirstName() + "|"
					+ ub.getLastName() + "|" + ub.getPhoneNumber() + "|" + ub.getEmail() + "|" + ub.getPicture() + "|";
			if (ub.isBlocked()) {
				newUserLine = newUserLine + "1|";
			} else {
				newUserLine = newUserLine + "0|";
			}

			if (ub instanceof Admin) {
				newUserLine = newUserLine + "admin";
			} else if (ub instanceof RegularUser) {
				newUserLine = newUserLine + "regular";
			}

			try {
				writer.write(newUserLine);
				if (c < users.size()) {
					writer.newLine(); // Add new line
					c++;

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		writer.close();
	}

	// Save/Load Destinations

	public static ArrayList<Destination> loadDestinations() {
		ArrayList<Destination> destinations = new ArrayList<Destination>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(destinationsPath));
			String line = reader.readLine();
			while (line != null) {
				String[] l = line.split("\\|");
				boolean active;

				if (l[6].equals("0")) {
					active = false;
				} else {
					active = true;
				}
				Destination dest = new Destination(l[0], l[1], l[2], l[3], l[4], l[5], active);
				destinations.add(dest);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return destinations;
	}

	public static void saveDestinations(ArrayList<Destination> destinations) throws IOException {
		BufferedWriter writer;
		writer = new BufferedWriter(new FileWriter(Util.destinationsPath, false) // Set true for append mode
		);
		int c = 1;
		for (Destination dest : destinations) {
			String newDestinationLine = dest.getName() + "|" + dest.getCountry() + "|" + dest.getAirport() + "|"
					+ dest.getAirportCode() + "|" + dest.getCoordinates() + "|" + dest.getPicture() + "|";
			if (dest.isActive()) {
				newDestinationLine = newDestinationLine + "1";
			} else {
				newDestinationLine = newDestinationLine + "0";
			}

			try {
				writer.write(newDestinationLine);
				if (c < destinations.size()) {
					writer.newLine(); // Add new line
					c++;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		writer.close();
	}

	public static ArrayList<Flight> loadFlights(ArrayList<Destination> destinations,
			ArrayList<Reservation> reservations) throws ParseException {
		ArrayList<Flight> flights = new ArrayList<Flight>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(flightsPath));
			String line = reader.readLine();
			while (line != null) {
				String[] l = line.split("\\|");

				Flight f = new Flight();
				f.setNumber(l[0]);
				f.setPrice(Float.parseFloat(l[6]));
				f.setPlaneModel(l[7]);
				f.setFirstClass(Integer.parseInt(l[8]));
				f.setBusinessClass(Integer.parseInt(l[9]));
				f.setEcoClass(Integer.parseInt(l[10]));
				f.setDate(new Date(Long.parseLong(l[11])));
				f.setFlightClass(FlightClass.values()[Integer.parseInt(l[12])]);

				/*
				 * Sets starting and arrival destinations. text file holds name and country of
				 * both destinations, and checks from every destination in system. This is done
				 * once, when server boots.
				 */
				Destination startDestination = null;
				Destination arrivalDestination = null;

				for (Destination dest : destinations) {
					if (startDestination != null && arrivalDestination != null) {
						break;
					}
					if (dest.getName().equals(l[1]) && dest.getCountry().equals(l[2])) {
						startDestination = dest;
					} else if (dest.getName().equals(l[3]) && dest.getCountry().equals(l[4])) {
						arrivalDestination = dest;
					}
				}

				f.setStartingDestination(startDestination);
				f.setArrivalDestination(arrivalDestination);

				// Add reservations.
				ArrayList<Reservation> res = new ArrayList<Reservation>();
				for (Reservation r : reservations) {
					String[] lineID = r.getId().split(" ");

					if (f.getNumber().equals(lineID[0])) {
						res.add(r);
					}
				}

				f.setReservations(res);

				flights.add(f);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flights;
	}

	public static void saveFlights(ArrayList<Flight> flights) throws IOException {
		BufferedWriter writer;
		writer = new BufferedWriter(new FileWriter(Util.flightsPath, false) // Set true for append mode
		);
		int c = 1;
		for (Flight f : flights) {
			String newDestinationLine = f.getNumber() + "|" + f.getStartingDestination().getName() + "|"
					+ f.getStartingDestination().getCountry() + "|" + f.getArrivalDestination().getName() + "|"
					+ f.getArrivalDestination().getCountry() + "| |" + f.getPrice() + "|" + f.getPlaneModel() + "|"
					+ f.getFirstClass() + "|" + f.getBusinessClass() + "|" + f.getEcoClass() + "|"
					+ f.getDate().getTime() + "|" + f.getFlightClass().ordinal();

			try {
				writer.write(newDestinationLine);
				if (c < flights.size()) {
					writer.newLine(); // Add new line
					c++;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		writer.close();
	}

	public static HashMap<String, Integer> loadReservationID() {
		HashMap<String, Integer> reservationID = new HashMap<String, Integer>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(reservationIDPath));
			String line = reader.readLine();
			while (line != null) {
				String[] l = line.split("\\|");
				reservationID.put(l[0], Integer.parseInt(l[1]));

				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return reservationID;
	}

	public static void saveReservationID(HashMap<String, Integer> reservationID) throws IOException {
		BufferedWriter writer;
		writer = new BufferedWriter(new FileWriter(reservationIDPath, false) // Set true for append mode
		);
		int c = 1;
		for (Entry<String, Integer> res : reservationID.entrySet()) {
			String resLine = res.getKey() + "|" + res.getValue();

			try {
				writer.write(resLine);
				if (c < reservationID.entrySet().size()) {
					writer.newLine(); // Add new line
					c++;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		writer.close();
	}

	public static ArrayList<Reservation> loadReservations(ArrayList<User> users) {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(reservationsPath));
			String line = reader.readLine();
			while (line != null) {
				String[] l = line.split("\\|");

				Reservation res = new Reservation();
				res.setId(l[0]);
				res.setDate(new Date(Long.parseLong(l[2])));
				res.setSeatClass(SeatClass.values()[Integer.parseInt(l[3])]);
				res.setNumberOfPassengers(Integer.parseInt(l[4]));

				for (User u : users) {
					if (u.getUsername().equals(l[1])) {
						res.setUser((RegularUser) u);
						break;
					}
				}

				reservations.add(res);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return reservations;
	}

	public static void saveReservations(ArrayList<Reservation> reservations) throws IOException {
		BufferedWriter writer;
		writer = new BufferedWriter(new FileWriter(reservationsPath, false) // Set true for append mode
		);
		int c = 1;
		for (Reservation res : reservations) {
			String resLine = res.getId() + "|" + res.getUser().getUsername() + "|" + res.getDate().getTime() + "|"
					+ res.getSeatClass().ordinal() + "|" + res.getNumberOfPassengers();

			try {
				writer.write(resLine);
				if (c < reservations.size()) {
					writer.newLine(); // Add new line
					c++;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		writer.close();
	}

}
