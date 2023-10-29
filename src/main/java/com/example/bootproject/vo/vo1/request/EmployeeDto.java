package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EmployeeDto {


    private String password;

    private String name;

    private boolean attendanceManager;

    private Date hireYear;



}
