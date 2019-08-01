package beans;

public class ChangePasswordBean {
	private String oldPassword;
	private String newPassword;
	private String repeatedPassword;

	public ChangePasswordBean() {
	}

	public ChangePasswordBean(String oldPassword, String newPassword, String repeatedPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.repeatedPassword = repeatedPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

}
