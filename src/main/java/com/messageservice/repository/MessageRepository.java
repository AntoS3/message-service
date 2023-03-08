package com.messageservice.repository;

import com.messageservice.entity.message.Message;
import com.messageservice.entity.message.SequenceNumber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, Integer> {

    @Query("select m from Message m where m.recipientId = ?1")
    List<Message> findByRecipientId (Integer recipientId);
    Message findBySequenceNumber(SequenceNumber sequenceNumber);
    void deleteBySequenceNumber(SequenceNumber sequenceNumber);

}
