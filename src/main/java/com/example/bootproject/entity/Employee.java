package com.example.bootproject.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Employee {
    String employeeId;
    String name;
    String password;
    boolean attendanceManager = false;
    LocalDate hireYear;

    public boolean isManager() {
        return attendanceManager;
    }
}
