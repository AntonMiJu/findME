package com.findme.controller.statusControllers;

import com.findme.exceptions.SystemException;
import com.findme.interceptor.ValidateInterceptor;
import com.findme.models.User;
import com.findme.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Log4j
@RestController
public class UserStatusController {
    private UserService userService;

    @Autowired
    public UserStatusController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@ModelAttribute User user) throws Exception {
        log.info("UserController registerUser method");
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(HttpSession session, @RequestBody User user) throws Exception {
        log.info("UserController login method");
        User loginedUser = userService.login(user.getEmail(), user.getPassword());

        session.setAttribute("user", loginedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    @Interceptors(ValidateInterceptor.class)
    public ResponseEntity<String> logout(HttpSession session) throws SystemException {
        log.info("UserController logout method");
        User user = (User) session.getAttribute("user");
        user.setDateLastActive(new Date());
        userService.update(user);

        session.setAttribute("user", null);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
