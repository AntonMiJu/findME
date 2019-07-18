package com.findme.dao;

import com.findme.exceptions.SystemException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GeneralDAO<T> {

    @PersistenceContext
    private EntityManager entityManager;

    public T get(Long id, Class<T> tClass) throws SystemException {
        try {
            return entityManager.find(tClass, id);
        } catch (Exception e) {
            throw new SystemException("Finding file is failed.");
        }
    }

    public void save(T t) throws SystemException {
        try {
            entityManager.persist(t);
        } catch (Exception e) {
            throw new SystemException("Saving file is failed.");
        }
    }

    public T update(T t) throws SystemException {
        try {
            return entityManager.merge(t);
        } catch (Exception e) {
            throw new SystemException("Updating file is failed.");
        }
    }

    public void delete(Long id, Class<T> tClass) throws SystemException {
        try {
            T t = entityManager.find(tClass, id);
            entityManager.remove(t);
        } catch (Exception e) {
            throw new SystemException("Deleting file is failed.");
        }
    }
}
