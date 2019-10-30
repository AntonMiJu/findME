package com.findme.dao;

import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Log4j
@Repository
@Transactional
public class UserDAO extends GeneralDAO<User> {
    private static final String findUserByPhoneOrEmail = "SELECT * FROM USERS WHERE PHONE = :phone OR EMAIL = :email ;";
    private static final String findUserByEmailAndPassword = "SELECT * FROM USERS WHERE EMAIL = :email AND PASSWORD = :pass ;";

    @PersistenceContext
    private EntityManager entityManager;

    public UserDAO() {
        settClass(User.class);
    }

    public User findUserByPhoneOrEmail(String phone, String email) throws SystemException {
        try {
            log.info("UserDAO findUserByPhoneOrEmail method. Finding by phone: " + phone + ", email: " + email);
            return (User) entityManager.createNativeQuery(findUserByPhoneOrEmail, User.class)
                    .setParameter("phone", phone)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (PersistenceException e) {
            log.error("User is not found");
            return null;
        } catch (Exception e) {
            log.error("findUserByPhoneOrEmail method is failed");
            throw new SystemException("500: Something gone wrong");
        }
    }

    public User findUserByEmailAndPassword(String email, String password) throws SystemException {
        try {
            log.info("UserDAO findUserByEmailAndPassword method.  Finding by email: " + email + ", password: " + password);
            return (User) entityManager.createNativeQuery(findUserByEmailAndPassword, User.class)
                    .setParameter("email", email)
                    .setParameter("pass", password)
                    .getSingleResult();
        } catch (PersistenceException e) {
            log.error("User is not found");
            return null;
        } catch (Exception e) {
            log.error("findUserByEmailAndPassword method is failed");
            throw new SystemException("500: Something gone wrong");
        }
    }

    @Override
    public User get(Long id) throws NotFoundException, SystemException {
        return super.get(id);
    }

    @Override
    public User save(User user) throws SystemException {
        return super.save(user);
    }

    @Override
    public User update(User user) throws SystemException {
        return super.update(user);
    }

    @Override
    public void delete(Long id) throws SystemException {
        super.delete(id);
    }
}
