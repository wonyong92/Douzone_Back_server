package com.example.bootproject.vo.vo3.request;

import lombok.Data;

@Data
public class LoginRequestDto {
    String loginId;
    String password;
    String ip;
}
