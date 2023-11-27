package com.example.bootproject.repository.mapper3.login;

import com.example.bootproject.vo.vo1.response.login.LoginResponseDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SessionLoginMapper {
    @Select("select * from auth where login_id=#{loginId} and ip = #{ip}")
    LoginResponseDto sessionLogin(@Param("loginId") String loginId, @Param("ip") String ip);

    @Insert("insert into auth values (#{sessionLoginId}, #{ip},now(),null) on duplicate key update login_time = now(), logout_time=null")
    void updateAuthInforamtion(@Param("sessionLoginId") String sessionLoginId, @Param("ip") String ip);

    @Insert("insert into auth values (#{sessionLoginId}, #{ip},now(),now()) on duplicate key update login_time = now(), logout_time=now()")
    void withOutAuthDataLogout(@Param("sessionLoginId") String sessionLoginId, @Param("ip") String ip);
    @Select("select attendance_manager from employee where employee_id=${loginId}")
    int isManager(String loginId);

    @Select("select count(*) > 0 from admin where admin_id=${loginId}")
    int isAdmin(String loginId);
}
