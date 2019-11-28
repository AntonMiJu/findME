package com.findme.controller.viewControllers;

import com.findme.exceptions.SystemException;
import com.findme.interceptor.ValidateInterceptor;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;

@Log4j
@Controller
@Interceptors(ValidateInterceptor.class)
public class RelationshipViewController {
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipViewController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @RequestMapping(path = "/income_requests", method = RequestMethod.GET)
    public String getIncomeRequests(HttpSession session) throws SystemException {
        log.info("RelationshipViewController getIncomeRequests method");
        relationshipService.getIncomeRequests(((User) session.getAttribute("user")).getId());
        return "profile";
    }

    @RequestMapping(path = "/outcome_requests", method = RequestMethod.GET)
    public String getOutcomeRequests(HttpSession session) throws SystemException {
        log.info("RelationshipViewController getOutcomeRequests method");
        relationshipService.getOutcomeRequests(((User) session.getAttribute("user")).getId());
        return "profile";
    }
}
