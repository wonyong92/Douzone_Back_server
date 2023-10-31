package com.example.bootproject.vo.vo1.request;


import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

//정규출퇴근시간
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
