package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo3.request.LoginRequestDto;
import com.example.bootproject.vo.vo3.response.login.LoginResponseDto;

public interface LoginService {

    LoginResponseDto sessionLogin(LoginRequestDto dto);

    LoginResponseDto formLogin(LoginRequestDto dto);
}
