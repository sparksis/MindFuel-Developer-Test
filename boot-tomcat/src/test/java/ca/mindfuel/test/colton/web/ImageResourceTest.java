package ca.mindfuel.test.colton.web;

import org.junit.Test;
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
@SpringBootTest(classes=Application.class,webEnvironment=WebEnvironment.RANDOM_PORT)
public class ImageResourceTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testImageNotFound() {
		ResponseEntity<byte[]> body = this.restTemplate.getForEntity("/rest/images/asdf", byte[].class);
		assertThat(body.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
