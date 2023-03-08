package com.messageservice.exception.message;

import feign.FeignException;
import org.springframework.http.HttpStatus;

public class RecipientNotFoundException extends FeignException {

    public RecipientNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "Recipient not found");
    }
}
