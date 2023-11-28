package com.example.bootproject.vo.vo1.response.notification;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationMessageResponseDto {
    Long messageId;
    String receiver;
    String message;
    LocalDateTime receiveTime;
    LocalDateTime readTime;
    String linkTo;
    String identifier;
    boolean forManger;
}
