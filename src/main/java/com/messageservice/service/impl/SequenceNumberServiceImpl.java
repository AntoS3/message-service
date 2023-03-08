package com.messageservice.service.impl;

import com.messageservice.entity.message.SequenceNumber;
import com.messageservice.repository.SequenceNumberRepository;
import com.messageservice.service.SequenceNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceNumberServiceImpl implements SequenceNumberService {

    private final SequenceNumberRepository repository;

    @Autowired
    public SequenceNumberServiceImpl(SequenceNumberRepository repository) {
        this.repository = repository;
    }

    @Override
    public SequenceNumber getSequenceNumber(SequenceNumber sequenceNumber) {
        return repository.findById(sequenceNumber.getSequenceNumberId()).orElseThrow();
    }

    @Override
    public SequenceNumber createSequenceNumber() {
        var sequenceNumber = new SequenceNumber();
        return repository.save(sequenceNumber);
    }

    @Override
    public void deleteSequenceNumber(SequenceNumber sequenceNumber) {
        repository.deleteById(sequenceNumber.getSequenceNumberId());
    }
}
