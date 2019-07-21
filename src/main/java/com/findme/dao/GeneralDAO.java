package com.findme.dao;

import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

public abstract class GeneralDAO<T> {

    @PersistenceContext
    private EntityManager entityManager;

    public T get(Long id, Class<T> tClass) throws NotFoundException, SystemException {
        try {
            return entityManager.find(tClass, id);
        }catch (PersistenceException e){
            throw new NotFoundException("404: File is not found.");
        }catch (Exception e) {
            throw new SystemException("500: Finding file is failed.");
        }
    }

    public void save(T t) throws SystemException {
        try {
            entityManager.persist(t);
        } catch (Exception e) {
            throw new SystemException("500: Saving file is failed.");
        }
    }

    public T update(T t) throws SystemException {
        try {
            return entityManager.merge(t);
        } catch (Exception e) {
            throw new SystemException("500: Updating file is failed.");
        }
    }

    public void delete(Long id, Class<T> tClass) throws SystemException {
        try {
            T t = entityManager.find(tClass, id);
            entityManager.remove(t);
        } catch (Exception e) {
            throw new SystemException("500: Deleting file is failed.");
        }
    }
}
