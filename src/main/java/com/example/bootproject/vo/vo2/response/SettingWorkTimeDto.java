package com.example.bootproject.vo.vo2.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class SettingWorkTimeDto {
    private int regular_time_adjustment_history_id;
    private LocalDate target_date;
    private LocalTime adjusted_start_time;
    private LocalTime adjusted_end_time;
    private String reason;
    private LocalDateTime regular_time_adjustment_time;

}
