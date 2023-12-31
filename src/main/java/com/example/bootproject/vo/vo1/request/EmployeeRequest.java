package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//사원정보테이블
@Data
@NoArgsConstructor
public class EmployeeRequest {

    private String employeeId;

    private String password;

    private String name;

    private boolean attendanceManager;

    private Date hireYear;
}
