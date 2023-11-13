package com.example.bootproject.vo.vo1.response.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeResponseDto {
    String employeeId;
    String name;
    boolean attendance_manager;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate hire_year;
}
