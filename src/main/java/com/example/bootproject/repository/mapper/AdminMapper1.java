package com.example.bootproject.repository.mapper;

import com.example.bootproject.vo.vo1.request.EmployeeInsertRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeInsertResponseDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper1 {


    @Insert("INSERT INTO employee (employee_id,password, name, attendance_manager, hire_year) VALUES (#{dto.employeeId},#{dto.passWord}, #{dto.name}, #{dto.attendanceManager}, now() )")
    int insertEmployee (@Param("dto") EmployeeInsertRequestDto dto);


    @Select("SELECT employee_id, password, name, attendance_manager, hire_year FROM employee WHERE employee_id = #{employeeId}")
    EmployeeInsertResponseDto selectEmployee(String employeeId);

//    //이거 사원id에 대해서 auto_increment할껀가..?
//    @Update("UPDATE employee_id  ")
//
//    @Update("UPDATE employee " +
//            "SET password = #{dto.passWord}, " +
//            "name = #{dto.name}, " +
//            "attendance_manager = #{dto.attendanceManager}, " +
//            "hire_year = now() " +
//            "WHERE employee_id = #{dto.employeeId}")




}
