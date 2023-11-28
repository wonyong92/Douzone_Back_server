package com.example.bootproject.vo.vo1.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AttendanceAppealHistory {
    private String employeeId;

    private Long attendanceAppealRequestId;

    private String status;

    private String reason;

    private Long attendanceInfoId;

    private Time appealedStartTime;

    private Time appealedEndTime;

    private LocalDateTime attendanceAppealRequestTime;

    private String reasonForRejection;

    private String name;


}
