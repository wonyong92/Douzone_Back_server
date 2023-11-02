package com.example.bootproject.vo.vo1.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;
import java.time.LocalTime;

//정규출퇴근시간
@Data
@NoArgsConstructor
public class RegularTimeAdjustmentHistoryRequestDto {


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime adjustedStartTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime adjustedEndTime;

    private String reason;

    private LocalDateTime regularTimeAdjustmentTime;

    private String employeeId;





}
