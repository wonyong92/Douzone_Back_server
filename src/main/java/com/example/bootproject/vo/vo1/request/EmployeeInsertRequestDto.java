package com.example.bootproject.vo.vo1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeInsertRequestDto {

    private String employeeId;

    private String passWord;

    private String name;

    private Boolean attendanceManager;

    @DateTimeFormat(pattern = "yyyy")
    private LocalDate hireYear;
}
