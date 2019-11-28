package com.findme.controller.handlers;

import com.findme.controller.viewControllers.MessageViewController;
import com.findme.controller.viewControllers.PostViewController;
import com.findme.controller.viewControllers.RelationshipViewController;
import com.findme.controller.viewControllers.UserViewController;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.ForbiddenException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(assignableTypes = {MessageViewController.class, PostViewController.class,
        RelationshipViewController.class, UserViewController.class})
public class ViewResponseHandler {
    @ExceptionHandler(value = BadRequestException.class)
    public ModelAndView badRequestHandler() {
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("message", "400: Bad request.");

        return modelAndView;
    }

    @ExceptionHandler(value = ForbiddenException.class)
    public ModelAndView forbiddenHandler() {
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("message", "403: Access forbidden.");

        return modelAndView;
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ModelAndView notFoundHandler() {
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("message", "404: File not found.");

        return modelAndView;
    }

    @ExceptionHandler(value = SystemException.class)
    public ModelAndView systemHandler() {
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("message", "500: Sorry, something gone wrong.");

        return modelAndView;
    }
}
