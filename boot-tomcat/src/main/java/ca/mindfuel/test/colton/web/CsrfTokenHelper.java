package ca.mindfuel.test.colton.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ResponseBody
public class CsrfTokenHelper {

	/**
	 * This file will load the CSRF-TOKEN into a cookie used by angular for CSFR requests
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "csrf-token.js", method = RequestMethod.GET)
	public void getCsrfToken(HttpServletRequest request,HttpServletResponse response) {
		Cookie csrfCookie = new Cookie("X-CSRF-TOKEN", ((CsrfToken)request.getAttribute("_csrf")).getToken());
		csrfCookie.setHttpOnly(false);
		//TODO set an expiry in line with spring
		response.addCookie(csrfCookie);
	}
}
