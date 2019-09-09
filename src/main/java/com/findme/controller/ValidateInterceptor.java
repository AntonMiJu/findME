package com.findme.controller;

import com.findme.exceptions.ForbiddenException;
import org.apache.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.servlet.http.HttpSession;

public class ValidateInterceptor {
    private static final Logger log = Logger.getLogger(ValidateInterceptor.class);

    @AroundInvoke
    public void intercept(HttpSession session) throws ForbiddenException{
        if (session.getAttribute("user") == null){
            log.error("User is not logined");
            throw new ForbiddenException("You must be logined");
        }
    }
}
