package ca.mindfuel.test.colton.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import ca.mindfuel.test.colton.model.IdentifiableEntity;

/**
 * 
 * @author colton
 *
 * @param <ID>
 *            Id Type
 * @param <T>
 *            Entity Type
 */
public abstract class AbstractRepository<ID, T extends IdentifiableEntity<ID>> {

	private Class<T> clazz;

	public AbstractRepository(Class<T> clazz) {
		this.clazz = clazz;

	}

	protected abstract EntityManager getEntityManager();

	public List<T> selectAll() {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		cq.from(clazz);
		return em.createQuery(cq).getResultList();
	}
	
	public Optional<T> selectById(ID id){
		return Optional.ofNullable(getEntityManager().find(clazz, id));
	}
	
	public void insertOrUpdate(T entity){
		getEntityManager().merge(entity);
	}
	
	public void delete(T entity){
		getEntityManager().remove(entity);
	}

}
