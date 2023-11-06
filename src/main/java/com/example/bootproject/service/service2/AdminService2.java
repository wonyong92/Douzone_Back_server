package com.example.bootproject.service.service2;

import com.example.bootproject.vo.vo2.response.EmployeeDto;
import com.example.bootproject.vo.vo2.response.Page;
import com.example.bootproject.vo.vo2.request.PagingRequestDto;

import java.util.List;

public interface AdminService2 {
    public Page<List<EmployeeDto>> getEmpInfo(PagingRequestDto pagingRequestDto);
    public EmployeeDto getOneEmpInfo(String employeeId);
    public int getEmployeeCheck (String id);
}
