package com.example.bootproject.vo.vo1.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AttendanceApprovalResponseDto {

    private Long attendanceApprovalId;

    private Long attendanceInfoId ;

    private LocalDateTime attendanceApprovalDate;

    private String employeeId;



}
