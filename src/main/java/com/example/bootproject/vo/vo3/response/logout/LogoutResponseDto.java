package com.example.bootproject.vo.vo3.response.logout;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogoutResponseDto {
    private String loginId;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String ip;
}
