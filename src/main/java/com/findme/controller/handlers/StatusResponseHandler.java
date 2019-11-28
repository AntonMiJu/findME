package com.findme.controller.handlers;

import com.findme.controller.statusControllers.MessageStatusController;
import com.findme.controller.statusControllers.PostStatusController;
import com.findme.controller.statusControllers.RelationshipStatusController;
import com.findme.controller.statusControllers.UserStatusController;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.ForbiddenException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {MessageStatusController.class, PostStatusController.class,
        RelationshipStatusController.class, UserStatusController.class})
public class StatusResponseHandler {
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> badRequestHandler() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity<String> forbiddenHandler() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> notFoundHandler() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = SystemException.class)
    public ResponseEntity<String> systemHandler() {
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
}
