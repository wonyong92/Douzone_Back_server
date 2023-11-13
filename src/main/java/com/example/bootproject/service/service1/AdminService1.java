package com.example.bootproject.service.service1;

import com.example.bootproject.vo.vo1.request.EmployeeInsertRequestDto;
import com.example.bootproject.vo.vo1.request.EmployeeUpdateRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeResponseDto;
import com.example.bootproject.vo.vo1.request.PagingRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeDto;
import com.example.bootproject.vo.vo1.response.Page;

import java.util.List;

public interface AdminService1 {

    EmployeeResponseDto insertEmployee(EmployeeInsertRequestDto dto);

    EmployeeResponseDto updateEmployee(EmployeeUpdateRequestDto dto);

    int deleteEmployee(String employeeId);

    Page<List<EmployeeDto>> getEmpInfo(PagingRequestDto pagingRequestDto);

    EmployeeDto getOneEmpInfo(String employeeId);

    int getEmployeeCheck(String id);

    boolean toggleManager(String employeeId);
}
