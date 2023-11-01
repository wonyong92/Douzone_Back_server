package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.EmployeeMapper2;
import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService2Impl implements  EmployeeService2{

    private final EmployeeMapper2 empMapper2;

    @Override
    public List<VacationRequestDto> getHistoryOfUsedVacationOfMine(String id) {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> result = empMapper2.getHistoryOfUsedVacationOfMine(id);

        log.info("empMapper2.getHistoryOfUsedVacationOfMine(id)의 result : {}",result);
        return result;
    }

    @Override
    public List<VacationRequestDto> getHistoryOfRejectedVacationOfMine(String id) {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> result =  empMapper2.getHistoryOfRejectedVacationOfMine(id);

        log.info("empMapper2.getHistoryOfRejectedVacationOfMine(id)의 result : {}",result);
        return result;
    }

    @Override
    public List<VacationRequestDto> getHistoryOfUsedVacationOfEmployee(String id) {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> result= empMapper2.getHistoryOfUsedVacationOfEmployee(id);

        log.info("empMapper2.getHistoryOfUsedVacationOfEmployee(id) : {}",result);
        return result;
    }
}
