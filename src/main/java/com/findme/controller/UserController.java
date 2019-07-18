package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.NotFoundException;
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
    public String profile(Model model, @PathVariable String userId) throws Exception{
        try {
            User user = userService.get(Long.parseLong(userId));
            if (user == null)
                throw new NotFoundException("User is not found.");
            model.addAttribute("user", userService.get(Long.parseLong(userId)));
        } catch (NumberFormatException e){
            throw new BadRequestException("You write incorrect id.");
        }
        return "profile";
    }
}
