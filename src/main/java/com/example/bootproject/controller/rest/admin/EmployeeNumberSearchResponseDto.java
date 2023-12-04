package com.example.bootproject.controller.rest.admin;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeNumberSearchResponseDto {
    List<String> employeeId;
    Integer searchCount;

}
