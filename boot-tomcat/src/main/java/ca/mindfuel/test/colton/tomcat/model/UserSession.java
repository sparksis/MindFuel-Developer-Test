package ca.mindfuel.test.colton.tomcat.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Scope(WebApplicationContext.SCOPE_SESSION)
@XmlRootElement
public class UserSession {

	private boolean authenticated;

	private String username;

	/**
	 * returns the username iff <code>authenticated==true</code> 
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

}
