package com.example.bootproject.repository.mapper3.login;

import com.example.bootproject.vo.vo1.request.LoginRequestDto;
import com.example.bootproject.vo.vo1.response.login.LoginResponseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminLoginMapper {

    @Select("select admin_id as login_id,login_time,logout_time,ip from admin left join auth on auth.login_id = admin.admin_id where admin_id = #{dto.loginId} and password = #{dto.password}")
    LoginResponseDto adminLogin(@Param("dto") LoginRequestDto dto);

    @Insert("insert into auth values(#{dto.loginId},#{dto.ip},now(),null) ON DUPLICATE KEY UPDATE login_time=now(),logout_time=null")
    void updateAuthInforamtion(@Param("dto") LoginResponseDto loginResult);

    @Insert("insert into auth values(#{dto.loginId},#{dto.ip},now(),null) ON DUPLICATE KEY UPDATE login_time=now(),logout_time=null")
    void insertAuthInforamtion(@Param("dto") LoginResponseDto loginResult);
}
