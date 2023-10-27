package com.example.bootproject.repository.mapper.mapper2;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.vo.vo2.response.EmployeeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminMapper2 {

    // 사원의 정보를 전체 select함
    @Select("SELECT * FROM employee")
    public List<EmployeeDto> getEmpInfo();

    // 특정 사원 번호를 가진 사원의 정보를 select함
    @Select("SELECT * FROM employee WHERE employee_id= #{employeeId}")
    public EmployeeDto getOneEmpInfo(String employeeId);

}
