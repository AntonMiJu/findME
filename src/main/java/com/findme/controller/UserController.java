package com.findme.controller;

import com.findme.exceptions.SystemException;
import com.findme.interceptor.ValidateInterceptor;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Log4j
@Controller
public class UserController {
    private UserService userService;
    private RelationshipService relationshipService;

    @Autowired
    public UserController(UserService userService, RelationshipService relationshipService) {
        this.userService = userService;
        this.relationshipService = relationshipService;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    @Interceptors(ValidateInterceptor.class)
    public String profile(HttpSession session, Model model, @PathVariable Long userId) throws Exception {
        log.info("UserController profile method");
        User loginedUser = (User) session.getAttribute("user");
        User user = userService.get(userId);
        Relationship relationship;
        if (!loginedUser.getId().equals(userId)){
            relationship = relationshipService.get(loginedUser.getId(), userId);
            model.addAttribute("relationshipStatus", relationship.getStatus().toString());
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute User user) throws Exception {
        log.info("UserController registerUser method");
        userService.save(user);

        return "profile";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    //TODO paramts should be places in body
    //question for you -  why?
    public String login(HttpSession session, @RequestParam(name = "email") String email
            , @RequestParam(name = "password") String password, Model model) throws Exception {
        log.info("UserController login method");
        User user = userService.login(email, password);

        //TODO what method will return if user not found??

        session.setAttribute("user", user);
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    @Interceptors(ValidateInterceptor.class)
    public String logout(HttpSession session) throws SystemException {
        log.info("UserController logout method");
        User user = (User) session.getAttribute("user");
        user.setDateLastActive(new Date());
        userService.update(user);
        session.setAttribute("user", null);
        return "index";
    }
}
