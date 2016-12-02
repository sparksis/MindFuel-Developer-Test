package ca.mindfuel.test.colton.model;

import java.util.List;
import java.util.stream.Collectors;

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
	 * @return the username 
	 */
	public String getUsername() {
		return isAuthenticated() ? auth.getName() : null;
	}
	
	public List<String> getAuthorities(){
		return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}

	public boolean isAuthenticated() {
		for (GrantedAuthority a : auth.getAuthorities()) {
			if (a.getAuthority().equals("ROLE_USER"))
				return true;
		}
		return false;
	}

}
