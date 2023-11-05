package com.example.bootproject.vo.vo1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceApprovalUpdateResponseDto {

    private String employeeId;

    private LocalDate attendanceApprovalDate;

    private Long attendanceInfoId;


}
