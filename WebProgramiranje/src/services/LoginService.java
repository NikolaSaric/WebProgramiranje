package services;

import java.io.BufferedWriter;
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
import beans.UserRegisterBean;
import models.RegularUser;
import models.User;

@Path("/server")
public class LoginService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@SuppressWarnings("unchecked")
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

		// Checks if users are loaded in servlet context.
		if (ctx.getAttribute("users") == null) {
			ArrayList<User> users = Util.loadUsers();
			ctx.setAttribute("users", users);
		}

		String user = "";

		// Looks for user in ctx.users, if one is found adds it to session.
		for (User u : (ArrayList<User>) ctx.getAttribute("users")) {
			if (u.getUsername().equals(lb.getUsername()) && u.getPassword().equals(lb.getPassword())) {
				user = u.getUsername();
				request.setAttribute("loggedUser", u);
				break;
			}
		}
		if (user.equals("")) {
			return "No user found with matching username and password.";
		}
		return "Logged in as: " + user;
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String register(UserRegisterBean ub) {
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

		// Checks if users are loaded in servlet context.
		if (ctx.getAttribute("users") == null) {
			ArrayList<User> users = Util.loadUsers();
			ctx.setAttribute("users", users);
		}

		// Checks if username is taken.
		for (User u : (ArrayList<User>) ctx.getAttribute("users")) {
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

		// Appends the new user to file which contains all users.
		String newUserLine = ub.getUsername() + "|" + ub.getPassword() + "|" + ub.getFirstName() + "|"
				+ ub.getLastName() + "|" + ub.getPhoneNumber() + "|" + ub.getEmail() + "|" + ub.getPicture() + "|" + "0"
				+ "|" + "regular";

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(Util.usersPath, true) // Set true for append mode
			);
			writer.newLine(); // Add new line
			writer.write(newUserLine);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Adds new user to ctx.users.
		ArrayList<User> users = (ArrayList<User>) ctx.getAttribute("users");
		users.add(newUser);

		return "Successfully registered as: " + ub.getUsername();
	}

}
