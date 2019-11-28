package com.findme.controller.statusControllers;

import com.findme.interceptor.ValidateInterceptor;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;

@Log4j
@RestController
@Interceptors(ValidateInterceptor.class)
public class RelationshipStatusController {
    private RelationshipService relationshipService;
    private UserService userService;

    @Autowired
    public RelationshipStatusController(RelationshipService relationshipService, UserService userService) {
        this.relationshipService = relationshipService;
        this.userService = userService;
    }

    @RequestMapping(path = "/user/{userId}/send_request", method = RequestMethod.POST)
    public ResponseEntity<String> sendRequest(HttpSession session, Model model, @PathVariable Long userId) throws Exception {
        log.info("RelationshipController sendRequest method");
        relationshipService.save(((User) session.getAttribute("user")).getId(), userId);
        model.addAttribute("user", userService.get(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/user/{userId}/update_request", method = RequestMethod.PUT)
    public ResponseEntity<String> updateRequest(HttpSession session, Model model, @PathVariable Long userId, @RequestParam(name = "status") String status) throws Exception {
        log.info("RelationshipController updateRequest method");
        relationshipService.update(((User) session.getAttribute("user")).getId(), userId, status);
        model.addAttribute("user", userService.get(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/user/{userId}/delete", method = RequestMethod.PUT)
    public ResponseEntity<String> deleteFromFriends(HttpSession session, Model model, @PathVariable Long userId) throws Exception {
        log.info("RelationshipController deleteFromFriends method");
        relationshipService.update(((User) session.getAttribute("user")).getId(), userId, RelationshipStatus.DELETED.toString());
        model.addAttribute("user", userService.get(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
