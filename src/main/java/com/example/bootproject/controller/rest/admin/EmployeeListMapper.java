package com.example.bootproject.controller.rest.admin;

import com.example.bootproject.vo.vo1.request.PageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeListMapper {
    @Select("select employee_id from employee limit ${size} offset ${size*(pageRequest.page-1)}")
    public List<String> getEmployeeNumbers(PageRequest pageRequest,int size);
    @Select("select count(*) from employee")
    int countAllEmployee();
}
