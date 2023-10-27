package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EmployeeDto {

    private String employee_id;

    private String password;

    private String name;

    private boolean attendance_manager;

    private Date hire_year;



}
