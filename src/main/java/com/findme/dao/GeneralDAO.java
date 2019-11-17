package com.findme.dao;

import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import lombok.extern.log4j.Log4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Log4j
@Transactional
public abstract class GeneralDAO<T> {
    private Class<T> tClass;

    @PersistenceContext
    private EntityManager entityManager;

    public T get(Long id) throws NotFoundException, SystemException {
        try {
            log.info("General DAO, get method. Getting " + tClass.toString());
            return entityManager.find(tClass, id);
        } catch (Exception e) {
            log.error("Getting " + id + " is failed");
            throw new SystemException("500: Finding file is failed.");
        }
    }

    public T save(T t) throws SystemException {
        try {
            log.info("General DAO, save method. Saving " + tClass.toString());
            entityManager.persist(t);
            return t;
        } catch (Exception e) {
            log.error("Saving " + tClass + " is failed");
            throw new SystemException("500: Saving file is failed.");
        }
    }

    public T update(T t) throws SystemException {
        try {
            log.info("General DAO, update method. Updating " + tClass.toString());
            return entityManager.merge(t);
        } catch (Exception e) {
            log.error("Updating " + tClass + " is failed");
            throw new SystemException("500: Updating file is failed.");
        }
    }

    public void delete(Long id) throws SystemException {
        try {
            log.info("General DAO, delete method. Deleting " + tClass.toString());
            T t = entityManager.find(tClass, id);
            entityManager.remove(t);
        } catch (Exception e) {
            log.error("Deleting " + tClass + " is failed");
            throw new SystemException("500: Deleting file is failed.");
        }
    }

    public void settClass(Class<T> tClass) {
        this.tClass = tClass;
    }
}
