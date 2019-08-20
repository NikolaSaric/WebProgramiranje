package services;

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

import beans.BlockUserBean;
import beans.RegularUserBean;
import models.Admin;
import models.RegularUser;
import models.User;

@Path("admin")
public class AdminService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<RegularUserBean> getAllUsers() {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof Admin)) {
			return null;
		}

		ArrayList<RegularUserBean> regularUsers = new ArrayList<RegularUserBean>();
		@SuppressWarnings("unchecked")
		ArrayList<User> users = (ArrayList<User>) ctx.getAttribute("users");

		for (User u : users) {
			if (u instanceof RegularUser) {
				RegularUserBean rub = new RegularUserBean();
				rub.setUsername(u.getUsername());
				rub.setFirstName(u.getFirstName());
				rub.setLastName(u.getLastName());
				rub.setEmail(u.getEmail());
				rub.setPhoneNumber(u.getPhoneNumber());
				rub.setBlocked(u.isBlocked());

				regularUsers.add(rub);
			}
		}

		return regularUsers;
	}

	@POST
	@Path("/blockUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean blockUser(BlockUserBean bub) throws IOException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (!(loggedUser instanceof Admin)) {
			return false;
		}

		if (bub.getUsername() == null || bub.getUsername().equals("".trim())) {
			return false;
		}

		@SuppressWarnings("unchecked")
		ArrayList<User> users = (ArrayList<User>) ctx.getAttribute("users");
		for (User u : users) {
			if (u.getUsername().equals(bub.getUsername())) {
				u.setBlocked(bub.isBlocked());
				return true;
			}
		}
		Util.saveUsers(users);

		return false;
	}

}
