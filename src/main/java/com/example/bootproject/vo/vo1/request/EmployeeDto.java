package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//사원정보
@Data
@NoArgsConstructor
public class EmployeeDto {


    private String password;

    private String name;

    private boolean attendanceManager;

    private Date hireYear;


}
