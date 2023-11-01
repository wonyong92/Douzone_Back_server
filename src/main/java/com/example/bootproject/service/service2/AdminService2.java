package com.example.bootproject.service.service2;

import com.example.bootproject.vo.vo2.response.EmployeeDto;
import com.example.bootproject.vo.vo2.response.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService2 {
    public Page<List<EmployeeDto>> getEmpInfo(int currentPage, String getSort, String desc);
    public EmployeeDto getOneEmpInfo(String employeeId);
}
