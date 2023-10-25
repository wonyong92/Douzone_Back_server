package com.example.bootproject.controller.rest.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController2 {
    @GetMapping("/admin/employee/information")
    public String getEmployeesInformation() {
        return "employee_Information";
    }// 주석

}
