package com.example.bootproject.service.service2;

import com.example.bootproject.vo.vo2.response.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService2 {
    public List<EmployeeDto> getEmpInfo();
    public EmployeeDto getOneEmpInfo(String employeeId);
}
