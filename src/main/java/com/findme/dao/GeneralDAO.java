package com.findme.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GeneralDAO<T> {

    @PersistenceContext
    private EntityManager entityManager;

    public T get(Long id, Class<T> tClass){
        return entityManager.find(tClass, id);
    }

    public void save(T t){
        entityManager.persist(t);
    }

    public T update(T t){
        return entityManager.merge(t);
    }

    public void delete(Long id, Class<T> tClass){
        T t = entityManager.find(tClass, id);
        entityManager.detach(t);
    }
}
