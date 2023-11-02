package com.example.bootproject.vo.vo1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@AllArgsConstructor
public class RegularTimeAdjustmentHistoryResponseDto {

    private Long regularTimeAdjustmentHistoryId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date targetDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private Time adjustedStartTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private Time adjustedEndTime;

    private String reason;

    private LocalDateTime regularTimeAdjustmentTime;

    private String employeeId;
}
