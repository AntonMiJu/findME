package com.findme.service.validate;

import com.findme.exceptions.BadRequestException;
import com.findme.models.ValidateDate;
import org.apache.log4j.Logger;

public class MaxQuantityOfFriends implements ValidateChain {
    private ValidateChain chain;
    private static final Logger log = Logger.getLogger(MaxQuantityOfFriends.class);

    @Override
    public void setNextChain(ValidateChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void validate(ValidateDate date) throws BadRequestException {
        log.info("MaxQuantityOfFriends chain, validate method");
        if (date.getQuantityOfFriendsFrom() >= 100 || date.getQuantityOfFriendsTo() >= 100){
            log.error("Too much friends");
            throw new BadRequestException("400: You or your opponent must have only 100 friends");
        }
        else
            this.chain.validate(date);
    }
}
