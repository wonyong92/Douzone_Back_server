package com.example.bootproject.vo.vo1.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AttendanceInfoResponseDto {

    private Long attendanceInfoId;

    private String attendanceStatusCategory;

    private String employeeId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDate AttendanceDate;



}
