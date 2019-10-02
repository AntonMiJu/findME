package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);
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
        User user = null;
        Relationship relationship = null;

        user = userService.get(userId);
//        if (!loginedUser.getId().equals(userId)){
//            relationship = relationshipService.get(loginedUser.getId(), userId);
//            model.addAttribute("relationshipStatus", relationship.getStatus().toString());
//        }

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
    public String login(HttpSession session, @RequestParam(name = "email") String email
            , @RequestParam(name = "password") String password, Model model) throws Exception {
        log.info("UserController login method");
        User user = null;

        user = userService.login(email, password);

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
