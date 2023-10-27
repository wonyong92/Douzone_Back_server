package com.example.bootproject.vo.vo2.response;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDto {
    private String employee_id; //사원번호
    private String password; //비밀번호
    private String name; //이름
    private boolean attendance_manager; //근태 담당자 여부
    private Date hire_year; // 입사 연도

}
