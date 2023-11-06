package com.example.bootproject.vo.vo1.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceApprovalInsertRequestDto {

    private Long attendanceInfoId;

    private String employeeId;

}
