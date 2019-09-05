package com.findme.dao;

import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Repository
@Transactional
public class UserDAO extends GeneralDAO<User> {
    private static final String findUserByPhoneOrEmail = "SELECT * FROM USERS WHERE PHONE = :phone OR EMAIL = :email;";
    private static final String findUserByEmailAndPassword = "SELECT * FROM USERS WHERE EMAIL = :email AND PASSWORD = :pass;";

    @PersistenceContext
    private EntityManager entityManager;

    public User findUserByPhoneOrEmail(String phone, String email) throws SystemException {
        try {
            return (User) entityManager.createNativeQuery(findUserByPhoneOrEmail, User.class)
                    .setParameter("phone", phone)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        } catch (Exception e) {
            throw new SystemException("500: Something gone wrong");
        }
    }

    public User findUserByEmailAndPassword(String email, String password) throws SystemException{
        try {
            return (User) entityManager.createNativeQuery(findUserByEmailAndPassword, User.class)
                    .setParameter("email", email)
                    .setParameter("pass", password)
                    .getSingleResult();
        } catch (PersistenceException e){
            return null;
        } catch (Exception e){
            throw new SystemException("500: Something gone wrong");
        }
    }

    @Override
    public User get(Long id, Class<User> userClass) throws NotFoundException, SystemException {
        return super.get(id, userClass);
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
    public void delete(Long id, Class<User> userClass) throws SystemException {
        super.delete(id, userClass);
    }
}
