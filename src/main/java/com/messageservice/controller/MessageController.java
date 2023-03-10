package com.messageservice.controller;

import com.messageservice.dto.MessageDtoRequest;
import com.messageservice.dto.MessageDtoResponse;
import com.messageservice.dto.MessageDtoUpdateRequest;
import com.messageservice.entity.message.SequenceNumber;
import com.messageservice.exception.message.MessageNotFoundException;
import com.messageservice.exception.message.RecipientNotFoundException;
import com.messageservice.service.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageServiceImpl service;

    @Autowired
    public MessageController(MessageServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public List<MessageDtoResponse> getAllMessage() throws MessageNotFoundException {
        return service.findAllMessage();
    }

    @GetMapping("{recipientId}")
    public List<MessageDtoResponse> getAllMessageByRecipient(@PathVariable ("recipientId") Integer recipientId,
                                                             @RequestHeader("Authorization") String jwt)
            throws MessageNotFoundException, RecipientNotFoundException {
        return service.findMessagesByRecipient(recipientId, jwt);
    }

    @GetMapping("sequence")
    public MessageDtoResponse getMessageBySequenceNumber(@RequestBody SequenceNumber sequenceNumber)
            throws MessageNotFoundException {
        return service.findMessageBySequenceNumber(sequenceNumber);
    }


    @PostMapping
    public MessageDtoResponse createMessage(@RequestBody MessageDtoRequest request,
                                            @RequestHeader("Authorization") String jwt) {
        return service.createMessage(request, jwt);
    }

    @PutMapping
    public MessageDtoResponse updateMessageById (@RequestBody MessageDtoUpdateRequest request)
            throws MessageNotFoundException, RecipientNotFoundException {
        return service.updateMessageById(request);
    }

    @DeleteMapping
    public void deleteMessageById(@RequestBody SequenceNumber sequenceNumber) throws MessageNotFoundException {
        service.deleteMessageBySequenceNumber(sequenceNumber);
    }
}
