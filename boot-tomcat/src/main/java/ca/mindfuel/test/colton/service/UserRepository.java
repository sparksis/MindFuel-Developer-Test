package ca.mindfuel.test.colton.service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import ca.mindfuel.test.colton.model.Image;
import ca.mindfuel.test.colton.model.User;
import ca.mindfuel.test.colton.model.User_;
import ca.mindfuel.test.colton.model.UserSession;

@Repository
public class UserRepository extends AbstractRepository<String, User> {
	
	public UserRepository() {
		super(User.class);
	}

	@Autowired
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Bean
	@Scope("request")
	public UserSession userSession() {
		return new UserSession();
	}

	@Bean
	@Scope("request")
	public User currentUser(UserSession userSession) {
		return selectByUsername(userSession.getUsername());
	}

	/**
	 * 
	 * @param username
	 * @return
	 * @deprecated use selectById
	 */
	@Deprecated
	public User selectByUsername(String username) {
		return selectById(username).orElse(null);
	}

}
