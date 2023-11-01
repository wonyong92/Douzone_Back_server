package com.example.bootproject.service.service2;

import com.example.bootproject.vo.vo2.response.EmployeeDto;

import com.example.bootproject.vo.vo2.response.SettingWorkTimeDto;

import com.example.bootproject.vo.vo2.response.VacationQuantitySettingDto;

import com.example.bootproject.vo.vo2.response.VacationRequestDto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ManagerService2 {
    public List<VacationRequestDto> getAllVacationHistory(String date);
    public List<VacationRequestDto> getEmpReqVacationHistory(String employeeId);
    public List<SettingWorkTimeDto> getSettingWorkTime();
    public List<VacationQuantitySettingDto> getVacationSettingHistory();
    public List<VacationRequestDto> getHistoryOfRejectedVacationOfEmployee(String employeeId);
}
