package com.example.bootproject.vo.vo1.request.notification;

import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Data
public class SseEmitterWithEmployeeInformationDto {
    String employeeNumber;
    String userType;
    SseEmitter sseEmitter;
}
