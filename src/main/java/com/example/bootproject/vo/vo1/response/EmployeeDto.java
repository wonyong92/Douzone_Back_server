package com.example.bootproject.vo.vo1.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class EmployeeDto {
    private String employeeId; //사원번호
    private String password; //비밀번호
    private String name; //이름
    private boolean attendanceManager; //근태 담당자 여부
    @DateTimeFormat(pattern = "yyyy")
    private Date hireYear; // 입사 연도

}
