package com.example.bootproject.vo.vo3.request.appeal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
public class AppealRequestDto {
    Long attendanceAppealRequestId;
    String reason;
    Long attendanceInfoId;
    @DateTimeFormat(pattern = "HH:mm:ss")
    LocalTime appealedStartTime;
    @DateTimeFormat(pattern = "HH:mm:ss")
    LocalTime appealedEndTime;
    @JsonIgnore
    String employeeId;
}
