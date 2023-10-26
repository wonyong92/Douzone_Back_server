package com.example.bootproject.repository.mapper.mapper2;

import com.example.bootproject.vo.vo2.response.EmployeeDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ManagerMapper2 {


    @Select("SELECT E.name, V.employee_id, V.result, V.vacation_start_time, V.vacation_end_time, V.REASON, V.vacation_request_time FROM EMPLOYEE E INNER JOIN vacation_request V ON E.employee_id = V.employee_id;")
    public List<EmployeeDto> getAllVacationHistory();
}
