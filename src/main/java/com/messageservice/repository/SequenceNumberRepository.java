package com.messageservice.repository;

import com.messageservice.entity.message.SequenceNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceNumberRepository extends JpaRepository<SequenceNumber, Integer> {
}
