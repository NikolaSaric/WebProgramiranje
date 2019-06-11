package beans;

public class UserBean {
	private String username;
	private String firstName;
	private String password;
	private String repeatPassword;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String picture;

	public UserBean(String username, String firstName, String password, String repeatPassword, String lastName,
			String phoneNumber, String email, String picture) {
		this.username = username;
		this.firstName = firstName;
		this.password = password;
		this.repeatPassword = repeatPassword;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.picture = picture;
	}

	public UserBean() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
