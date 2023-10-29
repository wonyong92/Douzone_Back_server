package com.example.bootproject.controller.rest.admin;

import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController3 {
    @PutMapping("/admin/manager/{employee_id}")
    public String toggleManager(@PathVariable(name = "employee_id") String employeeId) {
        return "toggleManager";
    }

    @GetMapping("/admin/download/{employee_id}")
    public String downloadEmployeePhoto(@PathVariable(name = "employee_id") String employeeId) {
        return "사원의 사진 파일 다운로드";
    }

    @PostMapping("/admin/upload")
    public String uploadEmployeePhoto() {
        return "사원의 사진 파일 업로드";
    }
}
