package com.messageservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDtoRequest {

    private Integer recipientId;
    private String messageText;
}
