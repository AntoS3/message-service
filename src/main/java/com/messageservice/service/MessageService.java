package com.messageservice.service;

import com.messageservice.dto.MessageDtoRequest;
import com.messageservice.dto.MessageDtoResponse;
import com.messageservice.dto.MessageDtoUpdateRequest;
import com.messageservice.entity.message.SequenceNumber;
import com.messageservice.exception.message.MessageNotFoundException;
import com.messageservice.exception.message.RecipientNotFoundException;

import java.util.List;

public interface MessageService {

    List<MessageDtoResponse> findAllMessage() throws MessageNotFoundException;
    List<MessageDtoResponse> findMessagesByRecipient(Integer recipientId, String jwt)
            throws MessageNotFoundException,
            RecipientNotFoundException;
    MessageDtoResponse findMessageBySequenceNumber(SequenceNumber sequenceNumber) throws MessageNotFoundException;
    MessageDtoResponse createMessage(MessageDtoRequest request, String jwt);
    MessageDtoResponse updateMessageById(MessageDtoUpdateRequest request)
            throws MessageNotFoundException,
            RecipientNotFoundException;
    void deleteMessageBySequenceNumber(SequenceNumber sequenceNumber) throws MessageNotFoundException;
}
