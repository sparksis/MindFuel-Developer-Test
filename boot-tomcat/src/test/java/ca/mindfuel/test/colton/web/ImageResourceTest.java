package ca.mindfuel.test.colton.web;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mindfuel.test.colton.Application;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ImageResourceTest {

	/**
	 * builds a realish looking byte[] which can be used for testing
	 * 
	 * @param filename
	 * @return
	 */
	public static byte[] buildImageData(String filename) {
		String r = "{filename:'";
		r += filename;
		r += "',data:[]}";
		return r.getBytes();
	}

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		restTemplate = restTemplate.withBasicAuth("user", "user");
	}

	@Test
	public void testImageNotFound() {
		ResponseEntity<byte[]> body = this.restTemplate.getForEntity("/rest/images/testImageNotFound", byte[].class);
		assertThat(body.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testCreateImage() {
		final String FILENAME = "testCreateImage";
		byte[] bytes = buildImageData(FILENAME);
		this.restTemplate.put("/rest/images/" + FILENAME, bytes);

		ResponseEntity<byte[]> response = this.restTemplate.getForEntity("/rest/images/" + FILENAME, byte[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(bytes);
	}

}
