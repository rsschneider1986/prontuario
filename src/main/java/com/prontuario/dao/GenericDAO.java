package com.prontuario.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class GenericDAO<T> {

    @PersistenceContext(unitName = "prontuarioPU")
    protected EntityManager em;

    private Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T save(T entity) {
        em.persist(entity);
        return entity;
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public void delete(T entity) {
        em.remove(em.merge(entity));
    }

    public T find(Object id) {
        return em.find(entityClass, id);
    }

    public List<T> listAll() {
        return em.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
    }
}
