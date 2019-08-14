package com.findme.service.validate;

import com.findme.exceptions.BadRequestException;
import com.findme.models.ValidateDate;

public class MaxOutcomeRequests implements ValidateChain {
    private ValidateChain chain;

    @Override
    public void setNextChain(ValidateChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void validate(ValidateDate date) throws BadRequestException{
        if (date.getQuantityOfOutcomeRequests()>=10)
            throw new BadRequestException("400: Too much outcome requests");
        else
            this.chain.validate(date);
    }
}
