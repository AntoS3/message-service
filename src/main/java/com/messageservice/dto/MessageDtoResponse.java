package com.messageservice.dto;

import com.messageservice.entity.message.SequenceNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDtoResponse {
    private SequenceNumber sequenceNumber;
    private Integer recipientId;
    private String messageText;
}
