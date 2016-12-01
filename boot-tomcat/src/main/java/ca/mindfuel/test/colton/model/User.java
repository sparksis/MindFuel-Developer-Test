package ca.mindfuel.test.colton.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

	/**
	 * borrowing from spring, yes the ID Should be a long
	 */
	@Id
	@Column(name = "USERNAME", nullable = false)
	private String username;
	@Column(name = "ENABLED", nullable = false)
	private boolean active;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
