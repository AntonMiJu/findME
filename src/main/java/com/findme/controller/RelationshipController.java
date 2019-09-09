package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;

@Controller
@Interceptors(ValidateInterceptor.class)
public class RelationshipController {
    private static final Logger log = Logger.getLogger(RelationshipController.class);
    private UserService userService;
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipController(UserService userService, RelationshipService relationshipService) {
        this.userService = userService;
        this.relationshipService = relationshipService;
    }

    @RequestMapping(path = "/user/{userId}/send_request", method = RequestMethod.POST)
    public String sendRequest(HttpSession session, Model model, @PathVariable Long userId) {
        log.info("RelationshipController sendRequest method");
        try {
            relationshipService.save(((User) session.getAttribute("user")).getId(), userId);
            model.addAttribute("user", userService.get(userId));
        } catch (SystemException e) {
            return "systemException";
        } catch (NotFoundException e) {
            return "notFoundException";
        } catch (BadRequestException e){
            return "badRequestException";
        }
        return "profile";
    }

    @RequestMapping(path = "/user/{userId}/update_request", method = RequestMethod.PUT)
    public String updateRequest(HttpSession session, Model model, @PathVariable Long userId, @RequestParam(name = "status") String status) {
        log.info("RelationshipController updateRequest method");
        try {
            relationshipService.update(((User) session.getAttribute("user")).getId(), userId, status);
            model.addAttribute("user", userService.get(userId));
        } catch (SystemException e) {
            return "systemException";
        } catch (NotFoundException e) {
            return "notFoundException";
        } catch (BadRequestException e){
            return "badRequestException";
        }
        return "profile";
    }

    @RequestMapping(path = "/user/{userId}/delete", method = RequestMethod.PUT)
    public String deleteFromFriends(HttpSession session, Model model, @PathVariable Long userId) {
        log.info("RelationshipController deleteFromFriends method");
        try {
            relationshipService.delete(((User) session.getAttribute("user")).getId(), userId);
            model.addAttribute("user", userService.get(userId));
        } catch (SystemException e) {
            return "systemException";
        } catch (NotFoundException e) {
            return "notFoundException";
        } catch (BadRequestException e){
            return "badRequestException";
        }
        return "profile";
    }

    @RequestMapping(path = "/income_requests", method = RequestMethod.GET)
    public String getIncomeRequests(HttpSession session){
        log.info("RelationshipController getIncomeRequests method");
        try {
            relationshipService.getIncomeRequests(((User)session.getAttribute("user")).getId());
        } catch (SystemException e){
            return "systemException";
        }
        return "profile";
    }

    @RequestMapping(path = "/outcome_requests", method = RequestMethod.GET)
    public String getOutcomeRequests(HttpSession session){
        log.info("RelationshipController getOutcomeRequests method");
        try {
            relationshipService.getOutcomeRequests(((User)session.getAttribute("user")).getId());
        } catch (SystemException e){
            return "systemException";
        }
        return "profile";
    }
}
