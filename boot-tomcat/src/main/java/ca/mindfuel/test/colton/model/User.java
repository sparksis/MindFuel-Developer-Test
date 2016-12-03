package ca.mindfuel.test.colton.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USERS")
public class User implements IdentifiableEntity<String> {

	/**
	 * borrowing from spring, yes the ID Should be a long
	 */
	@Id
	@Column(name = "USERNAME", nullable = false, length = 50)
	private String username;
	@Column(name = "PASSWORD", nullable = false, length = 500)
	private String password;
	@Column(name = "ENABLED", nullable = false)
	private boolean active;
	
	@Override
	@JsonIgnore
	public String getId(){
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
