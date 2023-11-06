package com.example.bootproject.vo.vo1.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeSearchResponseDto {


    private String employeeId;

    private String passWord;

    private String name;

    private Boolean attendanceManager;

    private LocalDate hireYear;
}
