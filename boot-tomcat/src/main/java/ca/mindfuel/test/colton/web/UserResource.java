package ca.mindfuel.test.colton.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import ca.mindfuel.test.colton.model.User;
import ca.mindfuel.test.colton.service.UserRepository;

@Transactional
@RestController
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class UserResource {

	@Autowired
	private UserRepository repository;

	private static User sanitizePassword(User user) {
		User r = new User(user);
		r.setPassword(null);
		return r;
	}

	@RequestMapping("/rest/users")
	public List<User> query() {
		return repository.selectAll()
				.stream().map(UserResource::sanitizePassword)
				.collect(Collectors.toList());
	}

	@RequestMapping(path = "/rest/users/{username}")
	public ResponseEntity<User> get(@PathVariable("username") String id) {
		Optional<User> dbUser = repository.selectById(id);
		if (dbUser.isPresent()) {
			return new ResponseEntity<>(sanitizePassword(dbUser.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(path = "/rest/users", method = RequestMethod.POST)
	public ResponseEntity<Void> save(User user) {
		Optional<User> dbUser = repository.selectById(user.getUsername());
		if (dbUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		repository.insertOrUpdate(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(path = "/rest/users/{username}", method = RequestMethod.PUT)
	public ResponseEntity<Void> save(@PathVariable("username") String username, User user) {
		// validate the request
		if (!user.getUsername().equals(username)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Optional<User> dbUser = repository.selectById(username);
		repository.insertOrUpdate(user);
		if (dbUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	}

}
