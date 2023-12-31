package com.example.bootproject.vo.vo1.response;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;

@Data
public class AttendanceAppealMediateResponseDto {

    private Long attendanceAppealRequestId;

    private String status;

    private String reason;

    private Long attendanceInfoId;

    private Time appealedStartTime;

    private Time appealedEndTime;

    private String employeeId;

    private LocalDateTime attendanceAppealRequestTime;

    private String reasonForRejection;

    private String message;


}
