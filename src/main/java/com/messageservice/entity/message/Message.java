package com.messageservice.entity.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "Messages")
public class Message {

    @BsonId
    private ObjectId id;
    private SequenceNumber sequenceNumber;
    private Integer recipientId;
    private String messageText;

}
