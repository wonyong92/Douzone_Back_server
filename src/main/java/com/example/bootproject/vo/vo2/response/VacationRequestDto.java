package com.example.bootproject.vo.vo2.response;

import lombok.Data;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
public class VacationRequestDto {
    private int vacation_request_key;
    private String vacation_category_key;
    private String employee_id;
    private String result;
    private int vacation_quantity;
    private LocalDate vacation_start_time;
    private LocalDate vacation_end_time;
    private LocalTime vacation_related_start_time;
    private LocalTime vacation_related_end_time;
    private String reason;
    private LocalDateTime vacation_request_time;
    private String reason_for_rejection;

}
