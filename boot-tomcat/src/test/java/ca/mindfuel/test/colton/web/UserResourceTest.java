package ca.mindfuel.test.colton.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mindfuel.test.colton.Application;
import ca.mindfuel.test.colton.web.UserResource.UserWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserResourceTest {

	/**
	 * builds a simple UserWrapper object.  Consumers are expected to set a unique username
	 * @return
	 */
	public static UserWrapper buildTestUser(){
		UserWrapper user = new UserWrapper();
		user.setActive(true);
		user.setPassword("password");
		user.getRolesWanted().add("USER");
		return user;
	}
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		restTemplate.withBasicAuth("admin", "admin");
	}
	
	@Test
	public void testCreateUserWithoutCredentials(){
		restTemplate.withBasicAuth("wrong", "wrong");
		UserWrapper user = buildTestUser();
		user.setUsername("testCreateUserWithoutCredentials");
		
		ResponseEntity<Void> body = this.restTemplate.postForEntity("/rest/users", user, Void.class);
		assertThat(body.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}
	

	@Test
	public void testCreateUser() {
		UserWrapper user = buildTestUser();
		user.setUsername("testCreateUser");

		ResponseEntity<Void> body = this.restTemplate.postForEntity("/rest/users", user, Void.class);
		assertThat(body.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

}
