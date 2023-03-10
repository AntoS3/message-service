package com.messageservice.service.impl;

import com.messageservice.entity.message.SequenceNumber;
import com.messageservice.repository.SequenceNumberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SequenceNumberServiceImplTest {

    @Mock
    SequenceNumberRepository repository;

    @InjectMocks
    SequenceNumberServiceImpl service;

    @Test
    public void testGetSequenceNumber() {

        var seqNum = new SequenceNumber(1);

        when(repository.findById(seqNum.getSequenceNumberId())).thenReturn(Optional.of(seqNum));
        assertEquals(seqNum, service.getSequenceNumber(seqNum));
    }

    @Test
    public void testCreateSequenceNumber() {

        service.createSequenceNumber();
        verify(repository).save(any());

    }

    @Test
    public void testDeleteSequenceNumber() {

        service.deleteSequenceNumber(new SequenceNumber());
        verify(repository).deleteById(anyInt());

    }
}