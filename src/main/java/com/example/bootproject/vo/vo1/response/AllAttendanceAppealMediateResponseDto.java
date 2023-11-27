package com.example.bootproject.vo.vo1.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AllAttendanceAppealMediateResponseDto {

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

    private String name;

    private Time startTime;

    private Time EndTime;

    private LocalDate attendanceDate;

}
