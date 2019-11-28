package com.findme.controller.viewControllers;

import com.findme.interceptor.ValidateInterceptor;
import com.findme.models.User;
import com.findme.service.MessageService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;

@Log4j
@Controller
@Interceptors(ValidateInterceptor.class)
public class MessageViewController {
    private MessageService messageService;

    @Autowired
    public MessageViewController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(path = "/messages/{userId}", method = RequestMethod.GET)
    public String getMessages(HttpSession session, Model model, @PathVariable Long userId) {
        log.info("MessageController getMessages method");
        model.addAttribute("messages", messageService.get(((User) session.getAttribute("user")).getId(), userId));
        return "messages";
    }
}
