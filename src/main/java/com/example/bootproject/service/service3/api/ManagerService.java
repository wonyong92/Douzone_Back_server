package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo3.response.Page;
import com.example.bootproject.vo.vo3.response.employee.EmployeeResponseDto;

import java.util.List;

public interface ManagerService {
    Page<List<EmployeeResponseDto>> getEmployeeList(int page, String sort, String desc);
}
