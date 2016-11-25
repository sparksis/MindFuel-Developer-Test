package ca.mindfuel.test.colton.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * An interchange object for passing session data between client/server Security
 * properties are read-only
 * 
 * @author colton
 *
 */
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
		for (GrantedAuthority a : auth.getAuthorities()) {
			if (a.getAuthority().equals("ROLE_USER"))
				return true;
		}
		return false;
	}

}
