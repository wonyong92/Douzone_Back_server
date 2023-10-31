package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo3.request.LoginRequestDto;
import com.example.bootproject.vo.vo3.response.login.LoginResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    LoginResponseDto sessionLogin(LoginRequestDto dto, HttpServletRequest req);

    LoginResponseDto formLogin(LoginRequestDto dto, HttpServletRequest req);
}
