package com.example.bootproject.vo.vo1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AttendanceApprovalInsertRequestDto {

    private Long attendanceInfoId;

    private String employeeId;

}
