package com.findme.service.validate;

import com.findme.exceptions.BadRequestException;
import com.findme.models.ValidateDate;
import org.apache.log4j.Logger;

public class MinTimeOfFriendship implements ValidateChain {
    private ValidateChain chain;
    private static final Logger log = Logger.getLogger(MinTimeOfFriendship.class);

    @Override
    public void setNextChain(ValidateChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void validate(ValidateDate date) throws BadRequestException{
        log.info("MinTimeOfFriendship chain, validate method");
        if (date.getDaysFriends() < 3){
            log.error("Not long friendships");
            throw new BadRequestException("400: Your friendships must be longer then 3 days");
        }
        else
            this.chain.validate(date);
    }
}
