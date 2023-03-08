package com.messageservice.service;

import com.messageservice.entity.message.SequenceNumber;

public interface SequenceNumberService {

    SequenceNumber getSequenceNumber(SequenceNumber sequenceNumber);

    SequenceNumber createSequenceNumber();

    void deleteSequenceNumber(SequenceNumber sequenceNumber);

}
