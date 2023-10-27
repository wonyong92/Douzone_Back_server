package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.EmployeeMapper2;
import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService2Impl implements  EmployeeService2{

    @Autowired
    private EmployeeMapper2 empMapper2;

    @Override
    public List<VacationRequestDto> getHistoryOfUsedVacationOfMine(String id) {
        return empMapper2.getHistoryOfUsedVacationOfMine(id);
    }
}
