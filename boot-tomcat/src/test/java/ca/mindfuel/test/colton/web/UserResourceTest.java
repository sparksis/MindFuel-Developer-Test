package ca.mindfuel.test.colton.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.Before;
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
import ca.mindfuel.test.colton.model.User;
import ca.mindfuel.test.colton.web.UserResource.UserWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserResourceTest {

	/**
	 * builds a simple UserWrapper object. Consumers are expected to set a
	 * unique username
	 * 
	 * @return
	 */
	public static UserWrapper buildTestUser() {
		UserWrapper user = new UserWrapper();
		user.setActive(true);
		user.setPassword("password");
		user.getRolesWanted().add("ROLE_USER");
		return user;
	}

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		restTemplate = restTemplate.withBasicAuth("admin", "admin");
	}

	@Test
	public void testCreateUserWithoutCredentials() {
		UserWrapper user = buildTestUser();
		user.setUsername("testCreateUserWithoutCredentials");

		ResponseEntity<Void> body = this.restTemplate.withBasicAuth("testCreateUserWithoutCredentials", "nothing")
				.postForEntity("/rest/users", user, Void.class);
		assertThat(body.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void testCreateUser() {
		UserWrapper user = buildTestUser();
		user.setUsername("testCreateUser");

		ResponseEntity<Void> body = this.restTemplate.postForEntity("/rest/users", user, Void.class);
		assertThat(body.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	public void testCreateInactiveUser() {
		final String NAME = "testCreateInactiveUser";
		UserWrapper user = buildTestUser();
		user.setUsername(NAME);
		user.setActive(false);

		ResponseEntity<Void> body = this.restTemplate.postForEntity("/rest/users", user, Void.class);
		assertThat(body.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		User createUser = this.restTemplate.getForObject("/rest/users/" + NAME, User.class);
		assertThat(createUser.isActive() == false);
	}

	@Test
	public void testQueryDoesNotReturnPassword() {
		User[] users = this.restTemplate.getForObject("/rest/users/", User[].class);
		assertThat(users.length).isGreaterThan(0);
		assertThat(Arrays.stream(users).allMatch(it -> it.getPassword() == null)).isTrue();
	}

	@Test
	public void testGetDoesNotReturnPassword() {
		User user = this.restTemplate.getForObject("/rest/users/admin", User.class);
		assertThat(user).isNotNull();
		assertThat(user.getPassword()).isNull();
	}

	@Test
	public void testUserNotFound() {
		ResponseEntity<User> user = this.restTemplate.getForEntity("/rest/users/testUserNotFound", User.class);
		assertThat(user.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testInactivateUser() {
		final String NAME = "testInactivateUser";
		UserWrapper user = buildTestUser();
		user.setUsername(NAME);
		user.setActive(true);

		//Create the user to inactivate
		ResponseEntity<Void> createBody = this.restTemplate.postForEntity("/rest/users", user, Void.class);
		assertThat(createBody.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		User createdUser = this.restTemplate.getForObject("/rest/users/" + NAME, User.class);
		assertThat(createdUser.isActive()).isTrue();

		//Update the user to inactive
		createdUser.setActive(false);
		this.restTemplate.put("/rest/users/" + NAME, new UserWrapper(createdUser));
		
		//find the inactive user
		User updatedUser = this.restTemplate.getForObject("/rest/users/" + NAME, User.class);
		assertThat(updatedUser.isActive()).isFalse();
	}

}
