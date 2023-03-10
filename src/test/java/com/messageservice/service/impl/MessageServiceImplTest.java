package com.messageservice.service.impl;

import com.messageservice.client.PersonClient;
import com.messageservice.dto.MessageDtoRequest;
import com.messageservice.dto.MessageDtoResponse;
import com.messageservice.dto.MessageDtoUpdateRequest;
import com.messageservice.entity.message.Message;
import com.messageservice.entity.message.SequenceNumber;
import com.messageservice.entity.person.Person;
import com.messageservice.exception.message.MessageNotFoundException;
import com.messageservice.exception.message.RecipientNotFoundException;
import com.messageservice.repository.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {

    @Mock
    MessageRepository repository;
    @Mock
    PersonClient client;
    @Mock
    ModelMapper mapper;
    @Mock
    SequenceNumberServiceImpl sequenceNumberService;
    @InjectMocks
    MessageServiceImpl service;


    public Message initMessage(Message message) {

        message.setRecipientId(1);
        message.setSequenceNumber(new SequenceNumber(1));
        message.setMessageText("text");

        return message;
    }

    @Test
    public void testFindAllMessage() throws MessageNotFoundException {

        List<Message> expectedList = new ArrayList<>();
        var message = new Message();
        expectedList.add(message);

        when(repository.findAll()).thenReturn(expectedList);

        assertEquals(expectedList.stream().map(
                msg -> spy(mapper).map(Message.class, MessageDtoResponse.class)).toList(),
                service.findAllMessage()
        );

    }

    @Test
    public void testFindAllMessageMessageNotFound() {

        assertThrows(MessageNotFoundException.class, () -> service.findAllMessage());

    }

    @Test
    public void testFindMessagesByRecipient() throws MessageNotFoundException {

        List<Message> expectedList = new ArrayList<>();
        var message = new Message();
        initMessage(message);
        expectedList.add(message);

        when(client.getPersonById(1,"jwt")).thenReturn(new Person());
        when(repository.findByRecipientId(1)).thenReturn(expectedList);

        assertEquals(expectedList.stream().map(
                        msg -> spy(mapper).map(Message.class, MessageDtoResponse.class)).toList(),
                service.findMessagesByRecipient(1, "jwt")
        );
    }

    @Test
    public void testFindMessagesByRecipientMessageNotFound() {

        List<Message> expectedList = new ArrayList<>();
        var message = new Message();
        initMessage(message);

        when(client.getPersonById(1,"jwt")).thenReturn(new Person());
        when(repository.findByRecipientId(1)).thenReturn(expectedList);

        assertThrows(MessageNotFoundException.class, () -> service.findMessagesByRecipient(1,"jwt"));
    }

    @Test
    public void testFindMessagesByRecipientPersonNotFound() {

        when(client.getPersonById(1,"jwt")).thenThrow(RecipientNotFoundException.class);

        assertThrows(RecipientNotFoundException.class, () -> service.findMessagesByRecipient(1,"jwt"));
    }

    @Test
    public void testFindMessageBySequenceNumber() throws MessageNotFoundException {

        var message = new Message();
        var seqNum = new SequenceNumber(1);

        when(repository.findBySequenceNumber(seqNum)).thenReturn(message);

        assertEquals(mapper.map(message, MessageDtoResponse.class), service.findMessageBySequenceNumber(seqNum));

    }

    @Test
    public void testFindMessageBySequenceNumberMessageNotFound() {

        var seqNum = new SequenceNumber(1);

        when(repository.findBySequenceNumber(seqNum)).thenReturn(null);

        assertThrows(MessageNotFoundException.class, () -> service.findMessageBySequenceNumber(seqNum));

    }

    @Test
    public void testCreateMessage() {

        var message = new Message();
        var expMessage = new MessageDtoResponse();
        var dtoReq = new MessageDtoRequest();
        var seqNum = new SequenceNumber(1);
        dtoReq.setRecipientId(1);
        dtoReq.setMessageText("text");

        when(mapper.map(dtoReq, Message.class)).thenReturn(message);
        when(sequenceNumberService.createSequenceNumber()).thenReturn(seqNum);
        when(mapper.map(repository.save(mapper.map(dtoReq, Message.class)), MessageDtoResponse.class)).thenReturn(expMessage);

        assertEquals(expMessage, service.createMessage(dtoReq,"jwt"));
    }

    @Test
    public void testCreateMessageRecipientNotFound() {

        var dtoReq = new MessageDtoRequest();
        dtoReq.setRecipientId(1);

        when(client.getPersonById(dtoReq.getRecipientId(),"jwt")).thenThrow(RecipientNotFoundException.class);

        assertThrows(RecipientNotFoundException.class, () -> service.createMessage(dtoReq,"jwt"));
    }

    @Test
    public void testUpdateMessageById() throws MessageNotFoundException {

        var seqNum = new SequenceNumber(1);
        var expMessage = new MessageDtoResponse();
        var dtoUpdateReq = new MessageDtoUpdateRequest();
        dtoUpdateReq.setSequenceNumber(seqNum);
        var oldMessage = new Message();
        var message = new Message();
        initMessage(oldMessage);

        when(repository.findBySequenceNumber(seqNum)).thenReturn(oldMessage);
        when(mapper.map(dtoUpdateReq, Message.class)).thenReturn(message);
        when(mapper.map(repository.save(mapper.map(oldMessage, Message.class)), MessageDtoResponse.class)).thenReturn(expMessage);

        assertEquals(expMessage, service.updateMessageById(dtoUpdateReq));

    }

    @Test
    public void testUpdateMessageByIdMessageNotFound() {

        var seqNum = new SequenceNumber(1);
        var dtoUpdateReq = new MessageDtoUpdateRequest();
        dtoUpdateReq.setSequenceNumber(seqNum);

        when(repository.findBySequenceNumber(seqNum)).thenReturn(null);

        assertThrows(MessageNotFoundException.class,() -> service.updateMessageById(dtoUpdateReq));

    }

    @Test
    public void testDeleteMessageBySequenceNumber() throws MessageNotFoundException {

        var seqNum = new SequenceNumber(1);
        var message = new Message();

        when(repository.findBySequenceNumber(seqNum)).thenReturn(message);

        service.deleteMessageBySequenceNumber(seqNum);

        verify(repository).deleteBySequenceNumber(seqNum);
    }

    @Test
    public void testDeleteMessageBySequenceNumberMessageNotFound() {

        var seqNum = new SequenceNumber(1);

        when(repository.findBySequenceNumber(seqNum)).thenReturn(null);

        assertThrows(MessageNotFoundException.class, () -> service.deleteMessageBySequenceNumber(seqNum));
    }
}