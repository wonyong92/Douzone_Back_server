package com.example.bootproject.vo.vo1.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AttendanceApprovalResponseDto {

    private Long attendanceApprovalId;

    private Long attendanceInfoId;

    private LocalDateTime attendanceApprovalDate;

    private String employeeId;


}
