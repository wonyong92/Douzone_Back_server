package com.example.bootproject.vo.vo1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

//퇴근
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceInfoEndRequestDto {

    private String employeeId;


    private String message;

    private LocalDate attendanceDate;


    private LocalDateTime endTime;


}
