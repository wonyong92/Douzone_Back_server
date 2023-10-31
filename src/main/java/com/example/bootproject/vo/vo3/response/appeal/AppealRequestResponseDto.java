package com.example.bootproject.vo.vo3.response.appeal;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppealRequestResponseDto {
    private Long attendanceAppealRequestId;
    private String status;
    private String reason;
    private Long attendanceInfoId;
    private String appealedStartTime;
    private String appealedEndTime;
    private String employeeId;
    private LocalDateTime attendanceAppealRequestTime;
    private String reasonForRejection;
}
