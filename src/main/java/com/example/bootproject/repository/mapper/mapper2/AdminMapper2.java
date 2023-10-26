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
    @Select("SELECT * FROM employee")
    public List<EmployeeDto> getEmpInfo();

}
