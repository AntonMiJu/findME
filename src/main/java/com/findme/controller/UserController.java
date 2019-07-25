package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId) {
        User user = null;
        try {
            user = userService.get(Long.parseLong(userId));
        } catch (NumberFormatException e) {
            return "badRequestException";
        } catch (SystemException e) {
            return "systemException";
        } catch (NotFoundException e) {
            return "notFoundException";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute User user) {
        try {
            userService.save(user);
        } catch (SystemException e){
            return "systemException";
        } catch(BadRequestException e){
            return "badRequestException";
        }
        return "profile";
    }
}
