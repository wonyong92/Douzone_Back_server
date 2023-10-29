package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo3.request.LoginRequestDto;
import com.example.bootproject.vo.vo3.response.login.LoginResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface LoginService {

    LoginResponseDto sessionLogin(LoginRequestDto dto);
    LoginResponseDto formLogin(LoginRequestDto dto);
}
