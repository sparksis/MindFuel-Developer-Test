package ca.mindfuel.test.colton.web;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

	public static class UserWrapper extends User {
		private List<String> rolesWanted;

		public List<String> getRolesWanted() {
			if (rolesWanted == null) {
				rolesWanted = new LinkedList<>();
			}
			return rolesWanted;
		}

		public User toUser() {
			return new User(this);
		}

		public org.springframework.security.core.userdetails.User toSpringUser() {
			List<GrantedAuthority> authorities = getRolesWanted().stream().map(it->new SimpleGrantedAuthority(it)).collect(Collectors.toList());
			org.springframework.security.core.userdetails.User u;
			u = new org.springframework.security.core.userdetails.User(this.getUsername(), this.getPassword(),authorities);
			return u;
		}
	}

	private static User sanitizePassword(User user) {
		User r = new User(user);
		r.setPassword(null);
		return r;
	}

	@Autowired
	private UserRepository repository;

	@Autowired
	private JdbcUserDetailsManager manager;

	@RequestMapping(path = "/rest/users/{username}")
	public ResponseEntity<User> get(@PathVariable("username") String id) {
		Optional<User> dbUser = repository.selectById(id);
		if (dbUser.isPresent()) {
			return new ResponseEntity<>(sanitizePassword(dbUser.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/rest/users")
	public List<User> query() {
		return repository.selectAll().stream().map(UserResource::sanitizePassword).collect(Collectors.toList());
	}

	@RequestMapping(path = "/rest/users/{username}", method = RequestMethod.PUT)
	public ResponseEntity<Void> save(@PathVariable("username") String username, @RequestBody UserWrapper user) {
		// validate the request
		if (!user.getUsername().equals(username)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Optional<User> dbUser = repository.selectById(username);
		if (dbUser.isPresent()) {
			if (user.getPassword() == null || user.getPassword().trim().equals("")) {
				user.setPassword(dbUser.get().getPassword());
			}
			manager.updateUser(user.toSpringUser());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return save(user);
		}
	}

	@RequestMapping(path = "/rest/users", method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody UserWrapper user) {
		Optional<User> dbUser = repository.selectById(user.getUsername());
		if (dbUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		if (user.getPassword() == null || user.getPassword().trim().equals("")) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		manager.createUser(user.toSpringUser());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
