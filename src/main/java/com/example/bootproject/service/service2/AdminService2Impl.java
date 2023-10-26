package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.AdminMapper2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService2Impl implements AdminService2 {
    @Autowired
    private AdminMapper2 mapper;


    @Override
    public List<EmployeeDto> getEmpInfo() {
        return mapper.getEmpInfo();
    }
}
