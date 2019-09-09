package com.findme.service.validate;

import com.findme.exceptions.BadRequestException;
import com.findme.models.ValidateDate;
import org.apache.log4j.Logger;

public class MaxOutcomeRequests implements ValidateChain {
    private ValidateChain chain;
    private static final Logger log = Logger.getLogger(MaxOutcomeRequests.class);

    @Override
    public void setNextChain(ValidateChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void validate(ValidateDate date) throws BadRequestException{
        log.info("MaxOutcomeRequests chain, validate method");
        if (date.getQuantityOfOutcomeRequests()>=10){
            log.error("Too much outcome requests");
            throw new BadRequestException("400: Too much outcome requests");
        }
        else
            this.chain.validate(date);
    }
}
