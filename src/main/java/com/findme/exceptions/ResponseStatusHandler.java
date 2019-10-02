package com.findme.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ResponseStatusHandler {

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
