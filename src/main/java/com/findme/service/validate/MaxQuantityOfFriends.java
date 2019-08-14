package com.findme.service.validate;

import com.findme.exceptions.BadRequestException;
import com.findme.models.ValidateDate;

public class MaxQuantityOfFriends implements ValidateChain {
    private ValidateChain chain;

    @Override
    public void setNextChain(ValidateChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void validate(ValidateDate date) throws BadRequestException {
        if (date.getQuantityOfFriendsFrom() >= 100 || date.getQuantityOfFriendsTo() >= 100)
            throw new BadRequestException("400: You or your opponent must have only 100 friends");
        else
            this.chain.validate(date);
    }
}
