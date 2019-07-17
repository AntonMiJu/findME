package com.findme.service;

import com.findme.dao.UserDAO;
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

    public User get(Long id) {
        return userDAO.get(id, User.class);
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public User update(User user) {
        return userDAO.update(user);
    }

    public void delete(Long id) {
        userDAO.delete(id, User.class);
    }
}
