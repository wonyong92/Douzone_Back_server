package com.example.bootproject.vo.vo1.request;


import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class RegularTimeAdjustmentHistoryDto {

    Date target_date;

    Time adjusted_start_time;

    Time Adjusted_end_time;

    String reason;

    LocalDateTime regular_time_adjustment_time;

    String employee_id;



}
