package com.example.bootproject.vo.vo1.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class SettingWorkTimeDto {

    private int regularTimeAdjustmentHistoryId; //대리키

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate targetDate; // 날짜

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime adjustedStartTime; // 조정 출근 시간

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime adjustedEndTime; // 조정 퇴근 시간

    private String reason; //사유

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regularTimeAdjustmentTime; // 조정 수행 날짜

    private String employeeId; // 사원-사원번호

    private String name; //사원 이름

}
