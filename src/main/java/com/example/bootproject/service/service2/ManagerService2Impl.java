package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.ManagerMapper2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;

import com.example.bootproject.vo.vo2.response.SettingWorkTimeDto;

import com.example.bootproject.vo.vo2.response.VacationQuantitySettingDto;

import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService2Impl implements  ManagerService2{

    private final ManagerMapper2 manMapper2;
    @Override
    public List<VacationRequestDto> getAllVacationHistory(String date) {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> result = manMapper2.getAllVacationHistory(date);

        log.info("manMapper2.getAllVacationHistory(date)의 result : {}",result);
        return result;
    }
    @Override
    public List<VacationRequestDto> getEmpReqVacationHistory(String employeeId) {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> result = manMapper2.getEmpReqVacationHistory(employeeId);

        log.info("manMapper2.getEmpReqVacationHistory(employeeId)의 result : {}",result);
        return result;
    }
    @Override
    public List<SettingWorkTimeDto> getSettingWorkTime() {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<SettingWorkTimeDto> result = manMapper2.getSettingWorkTime();

        log.info("manMapper2.getSettingWorkTime()의 result : {}",result);
        return result;
    }
    @Override
    public List<VacationQuantitySettingDto> getVacationSettingHistory() {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationQuantitySettingDto> result= manMapper2.getVacationSettingHistory();

        log.info("manMapper2.getVacationSettingHistory()의 result : {}",result);
        return result;
    }

    @Override
    public List<VacationRequestDto> getHistoryOfRejectedVacationOfEmployee(String employeeId) {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> result = manMapper2.getHistoryOfRejectedVacationOfEmployee(employeeId);

        log.info("manMapper2.getHistoryOfRejectedVacationOfEmployee(employeeId)의 result : {}",result);
        return result;
    }
}
