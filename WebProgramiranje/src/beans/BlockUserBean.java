package beans;

public class BlockUserBean {
	private String username;
	private boolean blocked;

	public BlockUserBean() {
	}

	public BlockUserBean(String username, boolean blocked) {
		this.username = username;
		this.blocked = blocked;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

}
