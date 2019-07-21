package com.findme.dao;

import com.findme.exceptions.SystemException;
import com.findme.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class UserDAO extends GeneralDAO<User> {
    private static final String findUserByPhone = "SELECT * FROM USERS WHERE PHONE = ?";
    private static final String findUserByEmail = "SELECT * FROM USERS WHERE EMAIL = ?";

    @PersistenceContext
    private EntityManager entityManager;

    public User findUserByPhone(String phone) throws SystemException{
        try {
            return (User) entityManager.createNativeQuery(findUserByPhone, User.class)
                    .setParameter(1, phone)
                    .getSingleResult();
        } catch (Exception e){
            throw new SystemException("500: Something gone wrong");
        }
    }

    public User findUserByEmail(String email) throws SystemException{
        try{
            return (User) entityManager.createNativeQuery(findUserByEmail,User.class)
                    .setParameter(1,email)
                    .getSingleResult();
        } catch (Exception e){
            throw new SystemException("500: Something gone wrong");
        }
    }
}
