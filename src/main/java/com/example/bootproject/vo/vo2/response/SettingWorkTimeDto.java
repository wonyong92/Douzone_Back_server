package com.example.bootproject.vo.vo2.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class SettingWorkTimeDto {
    private int regular_time_adjustment_history_id; //대리키
    private LocalDate target_date; // 날짜
    private LocalTime adjusted_start_time; // 조정 출근 시간
    private LocalTime adjusted_end_time; // 조정 퇴근 시간
    private String reason; //사유
    private LocalDateTime regular_time_adjustment_time; // 조정 수행 날짜
    private String employee_id; // 사원-사원번호


}
