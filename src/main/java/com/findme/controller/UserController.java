package com.findme.controller;

import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId) {
        try {
            User user = userService.get(Long.parseLong(userId));
            if (user == null)
                throw new NotFoundException("404: User is not found.");
            model.addAttribute("user", userService.get(Long.parseLong(userId)));
        } catch (NumberFormatException e){
            return "badRequestException";
        } catch (NotFoundException e){
            return "notFoundException";
        } catch (SystemException e){
            return "systemException";
        }
        return "profile";
    }
}
