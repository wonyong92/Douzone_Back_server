package com.example.bootproject.service.service1;

import com.example.bootproject.vo.vo1.request.EmployeeInsertRequestDto;
import com.example.bootproject.vo.vo1.request.EmployeeUpdateRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeResponseDto;

public interface AdminService1 {

    EmployeeResponseDto insertEmployee(EmployeeInsertRequestDto dto);

    EmployeeResponseDto updateEmployee(EmployeeUpdateRequestDto dto);

    int deleteEmployee(String employeeId);


}
