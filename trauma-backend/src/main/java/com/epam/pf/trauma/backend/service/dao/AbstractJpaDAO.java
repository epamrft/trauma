package com.epam.pf.trauma.backend.service.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractJpaDAO<T extends Serializable> {

	private Class<T> clazz;

	@PersistenceContext
	EntityManager entityManager;

	public void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	public T findOne(final int id) {
		return entityManager.find(clazz, id);
	}

	public Collection<T> findAll() {
		return entityManager.createQuery("FROM " + clazz.getName(), clazz).getResultList();
	}

	public void save(final T entity) {
		entityManager.persist(entity);
		// entityManager.flush();
	}

	public void update(final T entity) {
		entityManager.merge(entity);
	}

	public void delete(final T entity) {
		entityManager.remove(entity);
	}

	public T deleteById(final int entityId) {
		final T entity = findOne(entityId);
		delete(entity);
		return entity;
	}
}