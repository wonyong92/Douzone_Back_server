package com.example.bootproject.repository.mapper3.login;

import com.example.bootproject.vo.vo1.request.LoginRequestDto;
import com.example.bootproject.vo.vo1.response.login.LoginResponseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeLoginMapper {
    /*
    *  String loinId;
    LocalDateTime loginTime;
    LocalDateTime logoutTime;
    String ip;*/
    @Select("select name as employee_name,employee_id as login_id,login_time,logout_time,ip,attendance_manager as manager from employee left join auth on auth.login_id = employee.employee_id where employee_id = #{dto.loginId} and password = #{dto.password}")
    LoginResponseDto employeeLogin(@Param("dto") LoginRequestDto dto);

    @Insert("insert into auth values(#{dto.loginId},#{dto.ip},now(),null) ON DUPLICATE KEY UPDATE login_time=now(),logout_time=null")
    void updateAuthInforamtion(@Param("dto") LoginResponseDto loginResult);

    @Insert("insert into auth values(#{dto.loginId},#{dto.ip},now(),null) ON DUPLICATE KEY UPDATE login_time=now(),logout_time=null")
    void insertAuthInforamtion(@Param("dto") LoginResponseDto loginResult);
}
