package ca.mindfuel.test.colton.web;

import java.io.IOException;
import java.io.InputStream;

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

	@RequestMapping(path = { "/", "/auth", "admin" })
	public byte[] reroute() throws IOException {
		try (InputStream stream = RouteProvider.class.getResourceAsStream("/public/index.html")) {
			return StreamUtils.copyToByteArray(stream);
		}
	}

}
