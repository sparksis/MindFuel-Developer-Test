package ca.mindfuel.test.colton.web;

import static org.assertj.core.api.Assertions.assertThat;

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
public class UserIntegrationTest {

	private static final String ADMIN_USERNAME = "admin";

	private static final String ADMIN_PASSWORD = "admin";
	private static final String BILL_USERNAME = "bill";
	@Autowired
	private TestRestTemplate restTemplate;
	private String billPassword = "bill";

	private void activateBill() {
		User userFromServer = restTemplate.withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD).getForObject("/rest/users/"+BILL_USERNAME, User.class);
		UserWrapper user = new UserWrapper(userFromServer);
		user.setActive(true);
		restTemplate.withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD).put("/rest/users/"+BILL_USERNAME, user);
		
		ResponseEntity<byte[]> getResp = restTemplate.withBasicAuth(BILL_USERNAME, billPassword).getForEntity("/rest/images/", byte[].class);
		assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	private void changeBillsPassword() {
		User userFromServer = restTemplate.withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD).getForObject("/rest/users/"+BILL_USERNAME, User.class);
		UserWrapper user = new UserWrapper(userFromServer);
		user.setPassword(billPassword = "newPassword");
		restTemplate.withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD).put("/rest/users/"+BILL_USERNAME, user);
		
		ResponseEntity<byte[]> getResp = restTemplate.withBasicAuth(BILL_USERNAME, billPassword).getForEntity("/rest/images/", byte[].class);
		assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	private void createImageAsAdmin() {
		String imageName = "createImageAsAdmin";
		byte[] image = ImageResourceTest.buildImageData(imageName);
		restTemplate.withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD).put("/rest/images/" + imageName, image);
	}

	private void createImageAsBill() {
		String imageName = "createImageAsBill";
		byte[] image = ImageResourceTest.buildImageData(imageName);
		restTemplate.withBasicAuth(BILL_USERNAME, billPassword).put("/rest/images/" + imageName, image);
	}

	private void createUserBill() {
		UserWrapper user = new UserWrapper();
		user.setUsername(BILL_USERNAME);
		user.setPassword(billPassword);
		user.setActive(true);
		user.getRolesWanted().add("ROLE_USER");
		
		ResponseEntity<Void> resp = restTemplate.withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD).postForEntity("/rest/users/", user, Void.class);
		assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	private void deactivateBill() {
		User userFromServer = restTemplate.withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD).getForObject("/rest/users/"+BILL_USERNAME, User.class);
		UserWrapper user = new UserWrapper(userFromServer);
		user.setActive(false);
		restTemplate.withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD).put("/rest/users/"+BILL_USERNAME, user);
		
		ResponseEntity<byte[]> getResp = restTemplate.withBasicAuth(BILL_USERNAME, billPassword).getForEntity("/rest/images/", byte[].class);
		assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void test() {
		createImageAsAdmin();
		createUserBill();
		createImageAsBill();
		deactivateBill();
		activateBill();
		createImageAsBill();
		changeBillsPassword();
		createImageAsBill();
	}

}
