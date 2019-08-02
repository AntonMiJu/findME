package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.ForbiddenException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class RelationshipController {
    private UserService userService;
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipController(UserService userService, RelationshipService relationshipService) {
        this.userService = userService;
        this.relationshipService = relationshipService;
    }

    @RequestMapping(path = "/user/{userId}/send_request", method = RequestMethod.POST)
    public String sendRequest(HttpSession session, Model model, @PathVariable Long userId) {
        try {
            validateLogin(session);
            relationshipService.save(((User) session.getAttribute("user")).getId(), userId);
            model.addAttribute("user", userService.get(userId));
        } catch (SystemException e) {
            return "systemException";
        } catch (ForbiddenException e) {
            return "forbiddenException";
        } catch (NotFoundException e) {
            return "notFoundException";
        } catch (BadRequestException e){
            return "badRequestException";
        }
        return "profile";
    }

    @RequestMapping(path = "/user/{userId}/update_request", method = RequestMethod.PUT)
    public String updateRequest(HttpSession session, Model model, @PathVariable Long userId, @RequestParam(name = "status") String status) {
        try {
            validateLogin(session);
            relationshipService.update(((User) session.getAttribute("user")).getId(), userId, status);
            model.addAttribute("user", userService.get(userId));
        } catch (SystemException e) {
            return "systemException";
        } catch (ForbiddenException e) {
            return "forbiddenException";
        } catch (NotFoundException e) {
            return "notFoundException";
        } catch (BadRequestException e){
            return "badRequestException";
        }
        return "profile";
    }

    @RequestMapping(path = "/income_requests", method = RequestMethod.GET)
    public String getIncomeRequests(HttpSession session){
        try {
            validateLogin(session);
            relationshipService.getIncomeRequests(((User)session.getAttribute("user")).getId());
        } catch (SystemException e){
            return "systemException";
        } catch (ForbiddenException e){
            return "forbiddenException";
        }
        return "profile";
    }

    @RequestMapping(path = "/outcome_requests", method = RequestMethod.GET)
    public String getOutcomeRequests(HttpSession session){
        try {
            validateLogin(session);
            relationshipService.getOutcomeRequests(((User)session.getAttribute("user")).getId());
        } catch (SystemException e){
            return "systemException";
        } catch (ForbiddenException e){
            return "forbiddenException";
        }
        return "profile";
    }

    private void validateLogin(HttpSession session) throws ForbiddenException{
        if (session.getAttribute("user") == null)
            throw new ForbiddenException("You must be logined");
    }
}
