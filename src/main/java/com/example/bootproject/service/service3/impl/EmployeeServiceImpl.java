package com.example.bootproject.service.service3.impl;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.repository.mapper3.employee.EmployeeMapper;
import com.example.bootproject.service.service3.api.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;

    @Override
    public Employee findEmployeeInfoById(String loginId) {
        Employee find = employeeMapper.findEmployeeInfoById(loginId);
        return find;
    }
}
