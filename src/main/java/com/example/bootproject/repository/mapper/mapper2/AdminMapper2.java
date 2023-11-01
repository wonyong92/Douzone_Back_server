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

    // employee 테이블의 employee_id, password, name, attendance_manager, hire_year을 SELECT
    @Select("SELECT employee_id as employeeId, password, name, attendance_manager as attendanceManager, hire_year as hireYear\n" +
            "FROM employee\n" +
            "ORDER BY ${orderByCondition} ${desc}\n" +
            "LIMIT ${size} OFFSET ${startrow};")
    public List<EmployeeDto> getEmpInfo(int currentPage, int size, String orderByCondition, int startrow,String desc);


    // employee 테이블의 employee_id, password, name, attendance_manager, hire_year을 select 하여 특정 사원 번호 가진 사원의 정보 반환
    @Select("SELECT employee_id as employeeId, password, name, attendance_manager as attendanceManager, hire_year as hireYear FROM employee WHERE employee_id= #{employeeId}")
    public EmployeeDto getOneEmpInfo(String employeeId);


    @Select("select count(*) from employee;")
    public int getEmpInfoTotalRow();

}
