package com.example.bootproject.vo.vo1.response.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeResponseWithoutPasswordDto {
    String employeeId;
    String name;
    boolean attendanceManager;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate hireYear;
}
