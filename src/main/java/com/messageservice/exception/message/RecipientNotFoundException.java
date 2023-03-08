package com.messageservice.exception.message;

public class RecipientNotFoundException extends Exception {

    public RecipientNotFoundException() {
        super("Recipient not found");
    }
}
