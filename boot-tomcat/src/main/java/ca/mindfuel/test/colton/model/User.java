package ca.mindfuel.test.colton.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USERS")
public class User implements IdentifiableEntity<String> {

	public User() {
	}

	/**
	 * standard copy constructor
	 * 
	 * @param user
	 */
	public User(User user) {
		this.username = user.username;
		this.password = user.password;
		this.active = user.active;
	}

	/**
	 * borrowing from spring, yes the ID Should be a long
	 */
	@Id
	@Column(name = "USERNAME", nullable = false, length = 50)
	private String username;
	@Column(name = "PASSWORD", nullable = false, length = 500)
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ENABLED", nullable = false)
	private boolean active;

	@Override
	@JsonIgnore
	public String getId() {
		return getUsername();
	}

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
