package ca.mindfuel.test.colton.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.RequestAttributes;

import ca.mindfuel.test.colton.model.Image;
import ca.mindfuel.test.colton.model.User;
import ca.mindfuel.test.colton.model.UserSession;

@Repository
public class UserRepository {
	
	@Autowired
	private EntityManager em;
	
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
		// TODO Auto-generated method stub
		return null;
	}

}
