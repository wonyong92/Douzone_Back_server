package com.example.bootproject.vo.vo1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeResponseDto {

    private String employeeId;

    private String passWord;

    private String name;

    private Boolean attendanceManager;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireYear;



}
