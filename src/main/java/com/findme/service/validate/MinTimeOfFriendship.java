package com.findme.service.validate;

import com.findme.exceptions.BadRequestException;
import com.findme.models.ValidateDate;

public class MinTimeOfFriendship implements ValidateChain {
    private ValidateChain chain;

    @Override
    public void setNextChain(ValidateChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void validate(ValidateDate date) throws BadRequestException{
        if (date.getDaysFriends() < 3)
            throw new BadRequestException("400: Your friendships must be longer then 3 days");
        else
            this.chain.validate(date);
    }
}
