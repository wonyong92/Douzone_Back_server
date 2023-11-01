package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


//출근
@Data
@NoArgsConstructor
public class AttendanceInfoStartRequestDto {


    private String employeeId;

    private LocalDate attendanceDate;

    private LocalDateTime startTime;

    



}
