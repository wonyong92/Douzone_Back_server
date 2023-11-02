package com.example.bootproject.vo.vo1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AttendanceApprovalResponseDto {

    private Long attendanceApprovalId;

    private Long attendanceInfoId ;

    private LocalDateTime attendanceApprovalDate;

    private String employeeId;



}
