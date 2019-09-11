package services;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.ChangePasswordBean;
import beans.EditProfileBean;
import beans.LoginBean;
import beans.PictureBean;
import beans.UserBean;
import beans.UserRegisterBean;
import models.Admin;
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
	public UserBean login(LoginBean lb) {

		if (lb.getUsername().equals("".trim()) || lb.getUsername() == null) {
			return null;
		}
		if (lb.getPassword().equals("".trim()) || lb.getPassword() == null) {
			return null;
		}

		// Checks if users are loaded in servlet context.
		if (ctx.getAttribute("users") == null) {
			ArrayList<User> users = Util.loadUsers();
			ctx.setAttribute("users", users);
		}

		String user = "";
		UserBean ub = new UserBean();
		// Looks for user in ctx.users, if one is found adds it to session.
		for (User u : (ArrayList<User>) ctx.getAttribute("users")) {
			System.out.println(u.getClass());
			if (u.getUsername().equals(lb.getUsername()) && u.getPassword().equals(lb.getPassword())) {
				user = u.getUsername();
				request.getSession().setAttribute("loggedUser", u);

				ub.setUsername(u.getUsername());
				ub.setFirstName(u.getFirstName());
				ub.setLastName(u.getLastName());
				ub.setEmail(u.getEmail());
				ub.setPhoneNumber(u.getPhoneNumber());
				ub.setPicture(u.getPicture());
				ub.setBlocked(u.isBlocked());
				if (u instanceof Admin) {
					ub.setRole("Admin");
				} else if (u instanceof RegularUser) {
					ub.setRole("RegularUser");
				}
				break;
			}
		}
		if (user.equals("")) {
			return null;
		}

		return ub;
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
			return "Repeated password does not match new password.";
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
		// ctx.setAttribute("users", users);

		return "Successfully registered as: " + ub.getUsername();
	}

	@POST
	@Path("/editProfile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editProfile(EditProfileBean epb) throws IOException {
		if (epb.getFirstName() == null || epb.getFirstName().equals("".trim())) {
			return "Enter first name.";
		}
		if (epb.getLastName() == null || epb.getLastName().equals("".trim())) {
			return "Enter last name.";
		}
		if (epb.getEmail() == null || epb.getEmail().equals("".trim())) {
			return "Enter email.";
		}
		if (epb.getPhoneNumber() == null || epb.getPhoneNumber().equals("".trim())) {
			return "Enter phone number.";
		}

		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		System.out.println(loggedUser);
		System.out.println(loggedUser.getUsername());
		loggedUser.setFirstName(epb.getFirstName());
		loggedUser.setLastName(epb.getLastName());
		loggedUser.setEmail(epb.getEmail());
		loggedUser.setPhoneNumber(epb.getPhoneNumber());

		@SuppressWarnings("unchecked")
		ArrayList<User> users = (ArrayList<User>) ctx.getAttribute("users");

		Util.saveUsers(users);

		return "User profile successfully edited";
	}

	@POST
	@Path("/changePassword")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String changePassword(ChangePasswordBean cpb) throws IOException {
		if (cpb.getOldPassword() == null || cpb.getOldPassword().equals("".trim())) {
			return "Enter old password.";
		}
		if (cpb.getNewPassword() == null || cpb.getOldPassword().equals("".trim())) {
			return "Enter new password.";
		}
		if (cpb.getRepeatedPassword() == null || cpb.getRepeatedPassword().equals("".trim())) {
			return "Repeat new password.";
		}
		if (!cpb.getNewPassword().equals(cpb.getRepeatedPassword())) {
			return "Repeated password does not match new password.";
		}

		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!loggedUser.getPassword().equals(cpb.getOldPassword())) {
			return "Wrong user, please log in again.";
		}

		loggedUser.setPassword(cpb.getNewPassword());

		@SuppressWarnings("unchecked")
		ArrayList<User> users = (ArrayList<User>) ctx.getAttribute("users");
		Util.saveUsers(users);

		return "Successfully changed password";
	}

	@POST
	@Path("/uploadPicture")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String uploadPicture(PictureBean pb) throws IOException {
		System.out.println(pb.getImage());
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		loggedUser.setPicture(pb.getImage());
		@SuppressWarnings("unchecked")
		ArrayList<User> users = (ArrayList<User>) ctx.getAttribute("users");
		Util.saveUsers(users);
		return "true";
	}

}
