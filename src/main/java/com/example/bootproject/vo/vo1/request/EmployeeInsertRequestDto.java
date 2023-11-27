package com.example.bootproject.vo.vo1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeInsertRequestDto {
    @Length(min = 3, max = 10)
    @NotNull
    private String employeeId;
    @Length(min = 3, max = 10)
    @NotNull
    private String passWord;
    @Length(min = 2, max = 10)
    @NotNull
    private String name;

    private Boolean attendanceManager = false;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireYear;


}
