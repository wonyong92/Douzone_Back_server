package com.example.bootproject.controller.rest.admin;

import com.example.bootproject.service.service2.AdminService2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController2 {

    @Autowired
    private AdminService2 adminService;
    @GetMapping("/admin/employee/information")
    public List<EmployeeDto> getEmployeesInformation() {
        return adminService.getEmpInfo();
    }// 주석22

    @GetMapping("/admin/employee/information/{employee_id}")
    public EmployeeDto getEmployeeInformationByEmployeeId(@PathVariable(name = "employee_id") String employeeId) {
        return adminService.getOneEmpInfo(employeeId);
    }



}
