package com.example.bootproject.service.service3.impl;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.repository.mapper3.employee.EmployeeMapper;
import com.example.bootproject.service.service3.api.AdminService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final EmployeeMapper employeeMapper;

    @Override
    public boolean toggleManager(String employeeId) {
        log.info("employee ID {}", employeeId);
        Employee old = employeeMapper.findMemberByMemberId(employeeId);
        if (old != null) {
            TempDto dto = new TempDto();
            employeeMapper.toggleManager(dto, employeeId);
            Employee updated = employeeMapper.findMemberByMemberId(employeeId);
            log.info("old {} updated {}", old, updated);
            if (!updated.isManager() == old.isManager()) {
                return true;
            }
        }
        return false;
    }

    @Data
    public class TempDto {
        String generatedKey;
    }
}
