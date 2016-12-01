package ca.mindfuel.test.colton.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.mindfuel.test.colton.model.Image;
import ca.mindfuel.test.colton.model.User;

@Repository
public class ImageRepository {
	
	@Autowired
	private EntityManager em;

	public Image selectImageByFilenameAndUser(String filename, User currentUser) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertOrUpdate(Image image) {
		em.merge(image);
	}
	
	

}
