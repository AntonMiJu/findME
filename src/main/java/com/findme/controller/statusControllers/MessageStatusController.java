package com.findme.controller.statusControllers;

import com.findme.interceptor.ValidateInterceptor;
import com.findme.models.Message;
import com.findme.models.User;
import com.findme.service.MessageService;
import com.findme.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;

@Log4j
@RestController
@Interceptors(ValidateInterceptor.class)
public class MessageStatusController {
    private MessageService messageService;
    private UserService userService;

    @Autowired
    public MessageStatusController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @RequestMapping(path = "/messages/read", method = RequestMethod.PUT)
    public ResponseEntity<String> readMessage(@ModelAttribute Message message) throws Exception{
        log.info("MessageController readMessage method.");
        messageService.readMessage(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/send_message/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> writeMessage(HttpSession session, @ModelAttribute Message message, @PathVariable Long userId) throws Exception {
        log.info("MessageController writeMessage method");
        message.setUserFrom((User) session.getAttribute("user"));
        message.setUserTo(userService.get(userId));
        messageService.writeMessage(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/edit_message", method = RequestMethod.PUT)
    public ResponseEntity<String> editMessage(HttpSession session, @ModelAttribute Message message) throws Exception{
        log.info("MessageController updateMessage method");
        messageService.edit(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/delete_message/{messageId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) throws Exception{
        log.info("MessageController deleteMessage method");
        messageService.delete(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
