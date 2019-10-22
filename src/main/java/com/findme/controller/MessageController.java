package com.findme.controller;

import com.findme.models.Message;
import com.findme.models.User;
import com.findme.service.MessageService;
import com.findme.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;

@Controller
@Interceptors(ValidateInterceptor.class)
public class MessageController {
    private MessageService messageService;
    private UserService userService;
    private static final Logger log = Logger.getLogger(MessageController.class);

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @RequestMapping(path = "/messages/{userId}", method = RequestMethod.GET)
    public String getMessages(HttpSession session, Model model, @PathVariable Long userId) {
        log.info("MessageController getMessages method");
        model.addAttribute("messages", messageService.get(((User) session.getAttribute("user")).getId(), userId));
        return "messages";
    }

    @RequestMapping(path = "/messages/read", method = RequestMethod.PUT)
    public String readMessage(@ModelAttribute Message message) throws Exception{
        log.info("MessageController readMessage method.");
        messageService.readMessage(message);
        return "messages";
    }

    @RequestMapping(path = "/send_message/{userId}", method = RequestMethod.POST)
    public String writeMessage(HttpSession session, @ModelAttribute Message message, @PathVariable Long userId) throws Exception {
        log.info("MessageController writeMessage method");
        message.setUserFrom((User) session.getAttribute("user"));
        message.setUserTo(userService.get(userId));
        messageService.writeMessage(message);
        return "messages";
    }

    @RequestMapping(path = "/update_message", method = RequestMethod.PUT)
    public String updateMessage(HttpSession session, @ModelAttribute Message message) throws Exception{
        log.info("MessageController updateMessage method");
        messageService.update(message);
        return "messages";
    }

    @RequestMapping(path = "/delete_message/{messageId}", method = RequestMethod.DELETE)
    public String deleteMessage(@PathVariable Long messageId) throws Exception{
        log.info("MessageController deleteMessage method");
        messageService.delete(messageId);
        return "messages";
    }
}
