package services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import beans.UserBean;
import models.Admin;
import models.RegularUser;

@Path("admin")
public class AdminService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getLoggedAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	public UserBean getLoggedAdmin() {
		Admin admin = (Admin) ctx.getAttribute("loggedUser");

		if (admin != null) {
			UserBean ub = new UserBean();
			ub.setUsername(admin.getUsername());
			ub.setFirstName(admin.getFirstName());
			ub.setLastName(admin.getLastName());
			ub.setPhoneNumber(admin.getPhoneNumber());
			ub.setEmail(admin.getEmail());
			ub.setPicture(admin.getPicture());

			return ub;
		}
		return null;
	}
	
	@GET
	@Path("/logOut")
	@Produces(MediaType.APPLICATION_JSON)
	public String logOut() {
		ctx.setAttribute("loggedUser", null);
		
		return "You have logged out.";
	}
	
}
