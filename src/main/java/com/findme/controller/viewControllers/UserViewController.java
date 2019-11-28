package com.findme.controller.viewControllers;

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

@Log4j
@Controller
public class UserViewController {
    private UserService userService;
    private RelationshipService relationshipService;

    @Autowired
    public UserViewController(UserService userService, RelationshipService relationshipService) {
        this.userService = userService;
        this.relationshipService = relationshipService;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    @Interceptors(ValidateInterceptor.class)
    public String profile(HttpSession session, Model model, @PathVariable Long userId) throws Exception {
        log.info("UserViewController profile method");
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
}
