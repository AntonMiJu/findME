package com.findme.controller;

import com.findme.exceptions.ForbiddenException;

import javax.interceptor.AroundInvoke;
import javax.servlet.http.HttpSession;

public class ValidateInterceptor {
    @AroundInvoke
    public void intercept(HttpSession session) throws ForbiddenException{
        if (session.getAttribute("user") == null)
            throw new ForbiddenException("You must be logined");
    }
}
