package com.example.bootproject.controller.rest.admin;

import org.springframework.web.bind.annotation.*;

//@RestController
@RequestMapping("/admin")
public class AdminController1{

    @GetMapping("/admin/employee/information")
    public String getEmployeesInformation() {
        return "employee_Information";
    }

    @GetMapping("/admin/employee/information/{employee_id}")
    public String getEmployeeInformationByEmployeeId(@PathVariable(name = "employee_id") String employeeId) {
        return "employeeInformation";
    }


    @PutMapping("/admin/manager/{employee_id}")
    public String toggleManager(@PathVariable(name = "employee_id") String employeeId) {
        return "toggleManager";
    }

    @PostMapping("/admin/information/{employee_id}")
    public String modifyEmployeeInformation(@PathVariable(name = "employee_id") String employeeId) {
        return "Employee information modified for employee " + employeeId;
    }


    @PostMapping("/admin/register")
    public String registerEmployee() {
        return "Employee registered";
    }

    @DeleteMapping("/admin/information/{employee_id}")
    public String deleteEmployeeInformation(@PathVariable(name = "employee_id") String employeeId) {
        return "Employee information deleted for employee " + employeeId;
    }

    // 사원의 사진 파일 다운로드 (62)
    @GetMapping("/admin/download/{employee_id}")
    public String downloadEmployeePhoto(@PathVariable(name="employee_id") String employeeId){
        return "사원의 사진 파일 다운로드";
    }

    // 사원의 사진 파일 업로드 (64)
    @PostMapping("/admin/upload")
    public String uploadEmployeePhoto(){
        return "사원의 사진 파일 업로드";
    }
}