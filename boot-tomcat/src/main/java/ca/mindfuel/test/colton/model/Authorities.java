package ca.mindfuel.test.colton.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class is intended to be used by hibernate for generating the database definition to align with spring.
 * Users who wish to use this class should do so through spring.
 * 
 * See also: https://raw.githubusercontent.com/spring-projects/spring-security-javaconfig/master/spring-security-javaconfig/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
 *
 * 
 * @author colton
 * 
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "authorities")
public class Authorities implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
	private User user;
	@Id
	@Column(length = 50, nullable = false)
	private String authority;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
