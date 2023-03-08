package com.messageservice.config.errorDecoder;

import com.messageservice.exception.message.RecipientNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class MessageServiceErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new RecipientNotFoundException();
        }
        else return null;
    }
}
