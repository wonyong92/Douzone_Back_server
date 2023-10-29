package com.example.bootproject.vo.vo3.response.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class LoginResponseDto {
    private String loginId;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String ip;

    public LoginResponseDto(String loginId, String ip) {
        this.loginId = loginId;
        this.ip = ip;
    }
}
