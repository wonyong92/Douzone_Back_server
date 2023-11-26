package com.example.bootproject.vo.vo1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceApprovalUpdateResponseDto {

    private String employeeId;
    private LocalDateTime attendanceApprovalDate;
    private Long attendanceInfoId;
    private String message;
    private LocalDate attendanceDate;
}
