package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import models.Admin;
import models.RegularUser;
import models.User;

public class Util {
	final static String usersPath = "C:\\Users\\NikolaS\\Documents\\FAKS\\WEB\\Web Projekat\\WebProgramiranje\\WebProgramiranje\\src\\resources\\users.txt";

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
		for (User ub : users) {
			String newUserLine = ub.getUsername() + "|" + ub.getPassword() + "|" + ub.getFirstName() + "|"
					+ ub.getLastName() + "|" + ub.getPhoneNumber() + "|" + ub.getEmail() + "|" + ub.getPicture() + "|"
					+ "0" + "|";
			if(ub instanceof Admin) {
				newUserLine = newUserLine + "admin";
			} else if(ub instanceof RegularUser) {
				newUserLine = newUserLine + "regular";
			}

			try {
				writer.write(newUserLine);
				writer.newLine(); // Add new line

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		writer.close();
	}
}
