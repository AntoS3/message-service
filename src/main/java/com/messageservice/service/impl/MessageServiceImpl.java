package com.messageservice.service.impl;

import com.messageservice.client.PersonClient;
import com.messageservice.dto.MessageDtoRequest;
import com.messageservice.dto.MessageDtoResponse;
import com.messageservice.dto.MessageDtoUpdateRequest;
import com.messageservice.entity.message.Message;
import com.messageservice.entity.message.SequenceNumber;
import com.messageservice.exception.message.MessageNotFoundException;
import com.messageservice.exception.message.RecipientNotFoundException;
import com.messageservice.repository.MessageRepository;
import com.messageservice.service.MessageService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;
    private final PersonClient client;
    private final SequenceNumberServiceImpl sequenceNumberService;
    private final ModelMapper mapper;

    @Autowired
    public MessageServiceImpl(MessageRepository repository,
                              PersonClient client,
                              SequenceNumberServiceImpl sequenceNumberService,
                              ModelMapper mapper) {
        this.repository = repository;
        this.client = client;
        this.sequenceNumberService = sequenceNumberService;
        this.mapper = mapper;
    }

    @Override
    public List<MessageDtoResponse> findAllMessage() throws MessageNotFoundException {
        List<Message> messageList =  repository.findAll();
        if (!messageList.isEmpty()) {
            return messageList.stream()
                    .map(message -> mapper.map(message, MessageDtoResponse.class))
                    .toList();
        } else throw new MessageNotFoundException();
    }

    @Override
    public List<MessageDtoResponse> findMessagesByRecipient(Integer recipientId, String jwt)
            throws MessageNotFoundException, RecipientNotFoundException {

        try {
            client.getPersonById(recipientId, jwt);

            List<Message> messageList = repository.findByRecipientId(recipientId);
            if (!messageList.isEmpty()) {
                return messageList.stream()
                        .map(message -> mapper.map(message, MessageDtoResponse.class))
                        .toList();
            } else throw new MessageNotFoundException();
        } catch (FeignException e) {
            throw new RecipientNotFoundException();
        }
    }

    @Override
    public MessageDtoResponse findMessageBySequenceNumber(SequenceNumber sequenceNumber)
            throws MessageNotFoundException {
        if(repository.findBySequenceNumber(sequenceNumber) != null)
            return mapper.map(repository.findBySequenceNumber(sequenceNumber), MessageDtoResponse.class);
        else throw new MessageNotFoundException();
    }

    @Override
    public MessageDtoResponse createMessage(MessageDtoRequest messageDto, String jwt) {

        try {
            client.getPersonById(messageDto.getRecipientId(), jwt);

            var message = mapper.map(messageDto, Message.class);
            message.setSequenceNumber(sequenceNumberService.createSequenceNumber());
            repository.save(message);
            return mapper.map(message, MessageDtoResponse.class);

        } catch (FeignException e) {
            throw new RecipientNotFoundException();
        }
    }

    @Override
    public MessageDtoResponse updateMessageById(MessageDtoUpdateRequest messageDto)
            throws MessageNotFoundException,
            RecipientNotFoundException {

        if (repository.findBySequenceNumber(messageDto.getSequenceNumber()) != null) {
            var oldMessage = repository.findBySequenceNumber(messageDto.getSequenceNumber());

            var newMessage = mapper.map(messageDto, Message.class);
            newMessage.setRecipientId(oldMessage.getRecipientId());
            newMessage.setId(oldMessage.getId());

            return mapper.map(repository.save(newMessage), MessageDtoResponse.class);
        } else throw new MessageNotFoundException();

    }


    @Override
    public void deleteMessageBySequenceNumber(SequenceNumber sequenceNumber) throws MessageNotFoundException {
        if (repository.findBySequenceNumber(sequenceNumber) != null) {
            repository.deleteBySequenceNumber(sequenceNumber);
            sequenceNumberService.deleteSequenceNumber(sequenceNumber);
        } else throw new MessageNotFoundException();
    }
}
