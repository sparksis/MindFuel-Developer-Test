package ca.mindfuel.test.colton.tomcat.model;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

/**
 * An interchange object for passing session data between client/server 
 * Security properties are read-only
 * @author colton
 *
 */
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserSession {

	Authentication auth;

	public UserSession() {
		auth = SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * returns the username iff <code>authenticated==true</code>
	 * 
	 * @return
	 */
	public String getUsername() {
		return isAuthenticated() ? auth.getName() : null;
	}

	public boolean isAuthenticated() {
		return auth.getAuthorities().stream().anyMatch(it -> "USER".equals(it));
	}

}
