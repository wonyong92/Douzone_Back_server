package com.example.bootproject.repository.mapper3.login;

import com.example.bootproject.vo.vo3.response.login.LoginResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SessionLoginMapper {
    @Select("select * from auth where login_id=#{loginId}")
    LoginResponseDto sessionLogin(String loginId);
}
