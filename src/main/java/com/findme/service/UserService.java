package com.findme.service;

import com.findme.dao.UserDAO;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findUserByPhone(String phone) throws SystemException{
        return userDAO.findUserByPhone(phone);
    }

    public User findUserByEmail(String email) throws SystemException{
        return userDAO.findUserByEmail(email);
    }

    public User get(Long id) throws NotFoundException,SystemException {
        return userDAO.get(id, User.class);
    }

    public User save(User user) throws SystemException {
        return userDAO.save(user);
    }

    public User update(User user) throws SystemException {
        return userDAO.update(user);
    }

    public void delete(Long id) throws SystemException {
        userDAO.delete(id, User.class);
    }
}
