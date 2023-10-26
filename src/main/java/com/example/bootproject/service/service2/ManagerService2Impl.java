package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.ManagerMapper2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService2Impl implements  ManagerService2{

    @Autowired
    private ManagerMapper2 manMapper2;
    @Override
    public List<EmployeeDto> getAllVacationHistory() {
        return manMapper2.getAllVacationHistory();
    }
}
