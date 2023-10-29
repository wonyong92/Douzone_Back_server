package com.example.bootproject.controller.rest.manager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class ManagerController3 {
    @GetMapping("/manager/employees")
    public String getEmployeeList() {
        return "getEmployeeList";
    }

    @PostMapping("/manager/vacation/process")
    public String processVacationRequest() {
        return "processVacationRequest";
    }

    @PostMapping("/manager/vacation/modify/{employee_id}")
    public String modifyVacationQuantityOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        return "modifyVacationQuantityOfEmployee";
    }

    @PostMapping("/manager/appeal/process") //39
    public String processAppeal() {
        return "근태이상조정요청처리";
    }
}
