package com.findme.service.validate;

import com.findme.exceptions.BadRequestException;
import com.findme.models.ValidateDate;

public interface ValidateChain {
    void setNextChain(ValidateChain nextChain);

    void validate(ValidateDate date) throws BadRequestException;
}
