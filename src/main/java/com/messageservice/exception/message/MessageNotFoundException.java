package com.messageservice.exception.message;

public class MessageNotFoundException extends Exception {

    public MessageNotFoundException() {
        super("Message not found");
    }
}
