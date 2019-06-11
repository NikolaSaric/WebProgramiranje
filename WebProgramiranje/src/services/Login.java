package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.LoginBean;
import beans.UserBean;
import models.Admin;
import models.RegularUser;
import models.User;

@Path("/server")
public class Login {
	final String usersPath = "C:\\Users\\NikolaS\\Documents\\FAKS\\WEB\\Web Projekat\\WebProgramiranje\\WebProgramiranje\\src\\resources\\users.txt";

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(LoginBean lb) {

		if (lb.getUsername().equals("".trim()) || lb.getUsername() == null) {
			return "Enter username.";
		}
		if (lb.getPassword().equals("".trim()) || lb.getPassword() == null) {
			return "Enter password.";
		}

		ArrayList<User> users = this.loadUsers();

		String user = "";
		for (User u : users) {
			if (u.getUsername().equals(lb.getUsername()) && u.getPassword().equals(lb.getPassword())) {
				user = u.getUsername();
				ctx.setAttribute("loggedUser", u);
				break;
			}
		}
		if (user.equals("")) {
			return "No user found with matching username and password.";
		}
		return "Logged in as: " + user;
	}

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String register(UserBean ub) {
		if (ub.getUsername().equals("".trim()) || ub.getUsername() == null) {
			return "Enter username.";
		}
		if (ub.getPassword().equals("".trim()) || ub.getPassword() == null) {
			return "Enter password.";
		}
		if (ub.getRepeatPassword().equals("".trim()) || ub.getRepeatPassword() == null) {
			return "Repeat password.";
		}
		if (ub.getFirstName().equals("".trim()) || ub.getFirstName() == null) {
			return "Enter first name";
		}
		if (ub.getLastName().equals("".trim()) || ub.getLastName() == null) {
			return "Enter last name";
		}
		if (ub.getEmail().equals("".trim()) || ub.getEmail() == null) {
			return "Enter email";
		}
		if (ub.getPhoneNumber().equals("".trim()) || ub.getPhoneNumber() == null) {
			return "Enter phone number";
		}
		if (ub.getPicture().equals("".trim()) || ub.getPicture() == null) {
			ub.setPicture("Default picture");
		}
		if (!ub.getPassword().equals(ub.getRepeatPassword())) {
			return "Repeat password does not matches password.";
		}

		ArrayList<User> users = this.loadUsers();

		for (User u : users) {
			if (u.getUsername().equals(ub.getUsername())) {
				return "User with given name already exists.";
			}
		}

		RegularUser newUser = new RegularUser();
		newUser.setUsername(ub.getUsername());
		newUser.setPassword(ub.getPassword());
		newUser.setFirstName(ub.getFirstName());
		newUser.setLastName(ub.getLastName());
		newUser.setEmail(ub.getEmail());
		newUser.setPhoneNumber(ub.getPhoneNumber());
		newUser.setPicture(ub.getPicture());
		newUser.setBlocked(false);

		String newUserLine = ub.getUsername() + "|" + ub.getPassword() + "|" + ub.getFirstName() + "|"
				+ ub.getLastName() + "|" + ub.getPhoneNumber() + "|" + ub.getEmail() + "|" + ub.getPicture() + "|" + "0"
				+ "|" + "regular";

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(this.usersPath, true) // Set true for append mode
			);
			writer.newLine(); // Add new line
			writer.write(newUserLine);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Successfully registered as: " + ub.getUsername();
	}

	private ArrayList<User> loadUsers() {
		ArrayList<User> users = new ArrayList<User>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(this.usersPath));
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
}
