package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, @RequestParam(name = "email") String email
            , @RequestParam(name = "password") String password){
        User user = null;
        try {
                user = userService.login(email,password);
        } catch (NotFoundException e){
            return "forbiddenException";
        } catch (SystemException e){
            return "systemException";
        }
        session.setAttribute("user", user);
        return "index";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            user.setDateLastActive(new Date());
            userService.update(user);
            session.setAttribute("user", null);
            return "index";
        } catch (SystemException e){
            return "systemException";
        }
    }
}
