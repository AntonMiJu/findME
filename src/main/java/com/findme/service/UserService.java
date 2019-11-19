package com.findme.service;

import com.findme.dao.UserDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log4j
@Service
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User get(Long id) throws SystemException {
        log.info("UserService get method");
        return userDAO.get(id);
    }

    public User login(String email, String password) throws SystemException, BadRequestException {
        log.info("UserService login method");
        User user = userDAO.findUserByEmailAndPassword(email, password);
        if (user == null) {
            log.error("User with email " + email + " and password " + password + " not found");
            throw new BadRequestException("400: User not found");
        }
        return user;
    }

    public User save(User user) throws SystemException, BadRequestException {
        if (userDAO.findUserByPhoneOrEmail(user.getEmail(), user.getPhone()) != null) {
            log.error("User with email " + user.getEmail() + " and password " + user.getPhone() + " already exist");
            throw new BadRequestException("400: Save failed");
        }
        user.setDateRegistered(new Date());
        user.setDateLastActive(new Date());
        return userDAO.save(user);
    }

    public User update(User user) throws SystemException {
        log.info("UserService update method");
        return userDAO.update(user);
    }

    public void delete(Long id) throws SystemException {
        log.info("UserService delete method");
        userDAO.delete(id);
    }
}
