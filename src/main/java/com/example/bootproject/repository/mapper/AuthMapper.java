package com.example.bootproject.repository.mapper;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.vo.vo1.request.EmployeeInsertRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeInsertResponseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AuthMapper {
    @Select("SELECT * FROM auth WHERE employee_id = #{employeeId} and ip = #{ip}" )
    Employee authCheck(String employeeId, String ip);


}
