package com.example.bootproject.service.service3.impl;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.repository.mapper3.employee.EmployeeMapper;
import com.example.bootproject.service.service3.api.EmployeeService;
import com.example.bootproject.vo.vo3.request.employee.EmployeeInformationUpdateDto;
import com.example.bootproject.vo.vo3.response.employee.EmployeeResponseDto;
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

    @Override
    public EmployeeResponseDto updateInformation(String loginId, EmployeeInformationUpdateDto dto) {
        Employee find = employeeMapper.findEmployeeInfoById(loginId);

        if (find == null) {
            log.info("로그인된 유저의 정보를 찾지 못함");
            return null;
        }
        String oldPassword = dto.getOldPassword();
        String newPassword = dto.getNewPassword();
        if (find.getPassword().equals(oldPassword) && !oldPassword.equals(newPassword)) {
            int affected = employeeMapper.updatePassword(loginId, newPassword);
            if (affected != 1) {
                log.info("변경을 시도하였으나 수행 결과 변경된 컬럼이 없음");
                return null;
            }
            Employee updated = employeeMapper.findEmployeeInfoById(loginId);
            return new EmployeeResponseDto(updated.getEmployeeId(), updated.getName(), updated.isAttendanceManager(), updated.getHireYear());
        }

        return null;
    }
}
