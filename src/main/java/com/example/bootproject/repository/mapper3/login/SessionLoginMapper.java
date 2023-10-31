package com.example.bootproject.repository.mapper3.login;

import com.example.bootproject.vo.vo3.response.login.LoginResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SessionLoginMapper {
    @Select("select * from auth where login_id=#{loginId} and ip = #{ip}")
    LoginResponseDto sessionLogin(@Param("loginId") String loginId, @Param("ip") String ip);

    @Update("update auth set login_time=now() where login_id=#{sessionLoginId} and ip = #{ip}")
    void updateAuthInforamtion(@Param("sessionLoginId") String sessionLoginId, @Param("ip") String ip);
}
