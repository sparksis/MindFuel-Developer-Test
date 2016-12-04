package ca.mindfuel.test.colton.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mindfuel.test.colton.model.UserSession;

@RestController
public class SessionResource {

	@ResponseBody
	@RequestMapping(value = "rest/session", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserSession getSession(UserSession session) {
		return session;
	}
	
}
