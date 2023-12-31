package com.example.bootproject.vo.vo1.response.attendance;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceCheckResponse {
    Long attendanceInfoId;
    String attendanceStatusCategory;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
