package ca.mindfuel.test.colton.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.mindfuel.test.colton.model.Image;
import ca.mindfuel.test.colton.model.Image_;
import ca.mindfuel.test.colton.model.User;

@Repository
public class ImageRepository extends AbstractRepository<Long, Image> {
	
	public ImageRepository() {
		super(Image.class);
	}

	@Autowired
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	/**
	 * 
	 * @param filename
	 * @param currentUser
	 * @return the image from the database or null
	 */
	public Image selectImageByFilenameAndUser(String filename, User currentUser) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Image> cq = cb.createQuery(Image.class);
		Root<Image> image = cq.from(Image.class);
		cq.where(
			cb.and(
				cb.equal(image.get(Image_.filename), filename),
				cb.equal(image.get(Image_.user), currentUser)
			)
		);
		try {
			return em.createQuery(cq).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
