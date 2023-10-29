package com.example.bootproject.vo.vo1.request;


import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class RegularTimeAdjustmentHistoryDto {

    Date targetDate;

    Time adjustedStartTime;

    Time AdjustedEndTime;

    String reason;

    LocalDateTime regularTimeAdjustmentTime;

    String employeeId;



}
