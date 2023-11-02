package com.example.bootproject.vo.vo3.response.employee;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class EmployeeResponseDto {
    String employeeId;
    String name;
    boolean attendance_manager;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate hire_year;
}
