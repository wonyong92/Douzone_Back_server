package com.example.bootproject.vo.vo2.response;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDto {
    private String employee_id;
    private String password;
    private String name;
    private int attendance_manager;
    private Date hire_year;

}
