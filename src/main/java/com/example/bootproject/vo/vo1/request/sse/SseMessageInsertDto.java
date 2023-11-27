package com.example.bootproject.vo.vo1.request.sse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SseMessageInsertDto {
    Long messageId;
    String receiver;
    String message;
    String linkTo;
    String identifier;

    public SseMessageInsertDto(String receiver, String message, String linkTo, String identifier) {
        this.receiver = receiver;
        this.message = message;
        this.linkTo = linkTo;
        this.identifier = identifier;
    }
}
