package com.findme.controller;

import com.findme.exceptions.SystemException;
import com.findme.interceptor.ValidateInterceptor;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;

@Log4j
@Controller
@Interceptors(ValidateInterceptor.class)
public class RelationshipController {
    private UserService userService;
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipController(UserService userService, RelationshipService relationshipService) {
        this.userService = userService;
        this.relationshipService = relationshipService;
    }

    @RequestMapping(path = "/user/{userId}/send_request", method = RequestMethod.POST)
    public String sendRequest(HttpSession session, Model model, @PathVariable Long userId) throws Exception {
        log.info("RelationshipController sendRequest method");
        relationshipService.save(((User) session.getAttribute("user")).getId(), userId);
        model.addAttribute("user", userService.get(userId));
        return "profile";
    }

    @RequestMapping(path = "/user/{userId}/update_request", method = RequestMethod.PUT)
    public String updateRequest(HttpSession session, Model model, @PathVariable Long userId, @RequestParam(name = "status") String status) throws Exception {
        log.info("RelationshipController updateRequest method");
        relationshipService.update(((User) session.getAttribute("user")).getId(), userId, status);
        model.addAttribute("user", userService.get(userId));
        return "profile";
    }

    @RequestMapping(path = "/user/{userId}/delete", method = RequestMethod.PUT)
    public String deleteFromFriends(HttpSession session, Model model, @PathVariable Long userId) throws Exception {
        log.info("RelationshipController deleteFromFriends method");
        relationshipService.update(((User) session.getAttribute("user")).getId(), userId, RelationshipStatus.DELETED.toString());
        model.addAttribute("user", userService.get(userId));
        return "profile";
    }

    @RequestMapping(path = "/income_requests", method = RequestMethod.GET)
    public String getIncomeRequests(HttpSession session) throws SystemException {
        log.info("RelationshipController getIncomeRequests method");
        relationshipService.getIncomeRequests(((User) session.getAttribute("user")).getId());
        return "profile";
    }

    @RequestMapping(path = "/outcome_requests", method = RequestMethod.GET)
    public String getOutcomeRequests(HttpSession session) throws SystemException {
        log.info("RelationshipController getOutcomeRequests method");
        relationshipService.getOutcomeRequests(((User) session.getAttribute("user")).getId());
        return "profile";
    }
}
