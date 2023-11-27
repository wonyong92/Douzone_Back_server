package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo1.request.LoginRequestDto;
import com.example.bootproject.vo.vo1.response.login.LoginResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    LoginResponseDto sessionLogin(HttpServletRequest req);

    LoginResponseDto formLogin(LoginRequestDto dto, HttpServletRequest req);
}
