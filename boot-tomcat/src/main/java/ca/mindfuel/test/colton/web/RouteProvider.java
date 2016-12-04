package ca.mindfuel.test.colton.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A hacky class to route the applicable paths used by angular routing to the
 * index.html page
 * 
 * @author colton
 *
 */
@RestController
public class RouteProvider {

	@RequestMapping(path = { "/", "/login", "/admin", "/logout" })
	public byte[] reroute(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Cookie csrfCookie = new Cookie("X-CSRF-TOKEN", ((CsrfToken)request.getAttribute("_csrf")).getToken());
		csrfCookie.setHttpOnly(false);
		//TODO set an expiry in line with spring
		response.addCookie(csrfCookie);
		
		try (InputStream stream = RouteProvider.class.getResourceAsStream("/public/index.html")) {
			return StreamUtils.copyToByteArray(stream);
		}
	}

}
