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
public class UserRepository {
	
	@Autowired
	private EntityManager em;
	
	@Bean
	@Scope("request")
	public UserSession userSession(){
		return new UserSession();
	}
	
	@Bean
	@Scope("request")
	public User currentUser(UserSession userSession){
		return selectByUsername(userSession.getUsername());
	}

	public Image selectImageByFilenameAndUser(String filename, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public Image insertOrUpdateImageByFilenameAndUser(String filename, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public User selectByUsername(String username) {
		CriteriaBuilder cb =  em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.where(cb.equal(user.get(User_.username), username));
		return em.createQuery(cq).getSingleResult();
	}

}
