package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//근태승인내역테이블
@NoArgsConstructor
@Data
public class AttendanceApprovalInfoDto {

    private String employeeId;

    private String name;

    private Date attendanceApprovalDate;
}
