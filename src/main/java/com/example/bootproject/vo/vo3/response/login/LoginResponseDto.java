package com.example.bootproject.vo.vo3.response.login;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LoginResponseDto {
    private String loginId;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String ip;
    private boolean manager;
    private String message;
    public LoginResponseDto(String loginId, String ip) {
        this.loginId = loginId;
        this.ip = ip;
    }
}
