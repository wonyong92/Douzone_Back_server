package com.example.bootproject.service.service2;

import com.example.bootproject.vo.vo2.response.VacationRequestDto;

import java.util.List;

public interface EmployeeService2 {
    public List<VacationRequestDto> getHistoryOfUsedVacationOfMine(String id);
}
