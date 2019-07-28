package com.findme.service;

import com.findme.dao.UserDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User get(Long id) throws NotFoundException,SystemException {
        return userDAO.get(id, User.class);
    }

    public User login(String email, String password) throws SystemException, NotFoundException{
        User user = userDAO.findUserByEmailAndPassword(email,password);
        if (user == null)
            throw new NotFoundException("404: User not found");
        return user;
    }

    public User save(User user) throws SystemException, BadRequestException {
        if (userDAO.findUserByPhoneOrEmail(user.getEmail(), user.getPhone()) != null)
            throw new BadRequestException("Save failed");
        user.setDateRegistered(new Date());
        user.setDateLastActive(new Date());
        return userDAO.save(user);
    }

    public User update(User user) throws SystemException {
        return userDAO.update(user);
    }

    public void delete(Long id) throws SystemException {
        userDAO.delete(id, User.class);
    }
}
