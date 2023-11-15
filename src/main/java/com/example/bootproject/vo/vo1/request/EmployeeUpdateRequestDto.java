package com.example.bootproject.vo.vo1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeUpdateRequestDto {

    private String employeeId;

    private String passWord;

    private String name;

    private Boolean attendanceManager;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireYear;


}
