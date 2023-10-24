package com.example.bootproject.repository.mapper;

import com.example.bootproject.entity.Employee;
import org.apache.ibatis.annotations.Select;

public interface AuthMapper {
    @Select("SELECT * FROM auth WHERE employee_id = #{employeeId} and ip = #{ip}" )
    Employee authCheck(String employeeId, String ip);
}
