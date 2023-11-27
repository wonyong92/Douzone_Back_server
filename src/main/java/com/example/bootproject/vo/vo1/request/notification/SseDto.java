package com.example.bootproject.vo.vo1.request.notification;

import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Data
public class SseDto {
    private SseEmitter emitter;
    private String data;

    // 생성자, 게터, 세터 등 필요한 메서드를 추가

    public SseEmitter getEmitter() {
        return emitter;
    }

    public void setEmitter(SseEmitter emitter) {
        this.emitter = emitter;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
