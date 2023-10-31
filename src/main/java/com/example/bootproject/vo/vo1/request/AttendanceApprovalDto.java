package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


//근태정보 --승인 테이블에 승인 내역을남기는 dto
@Data
@NoArgsConstructor
public class AttendanceApprovalDto {
    private Long attendanceInfoId;

    private LocalDate attendanceApprovalDate;

    private String employeeId;


}
