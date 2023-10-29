package com.example.bootproject.repository.mapper3.login;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.vo.vo3.response.login.LoginResponseDto;
import com.example.bootproject.vo.vo3.response.logout.LogoutResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AuthMapper {


    @Select("SELECT * FROM auth WHERE employee_id = #{employeeId} and ip = #{ip}")
    Employee authCheck(String employeeId, String ip);

    @Select("SELECT * FROM auth WHERE login_id = #{loginId} and ip = #{ip}")
    LoginResponseDto checkAuthInformation(@Param("loginId") String loginId, @Param("ip") String ip);
    @Update("update auth set logout_time=now() where login_id=#{sessionLoginId}")
    void logout(String sessionLoginId);
    @Select("select * from auth where login_id = #{sessionLoginId}")
    LogoutResponseDto logoutResult(String sessionLoginId);
}
