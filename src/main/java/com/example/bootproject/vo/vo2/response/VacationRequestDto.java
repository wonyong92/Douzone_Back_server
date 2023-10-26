package com.example.bootproject.vo.vo2.response;

import lombok.Data;


import java.sql.Time;



@Data
public class VacationRequestDto {
    private int vacation_request_key;
    private String vacation_category_key;
    private String employee_id;
    private String result;
    private int vacation_quantity;
    private java.sql.Date vacation_start_time;
    private java.sql.Date vacation_end_time;
    private Time vacation_related_start_time;
    private Time vacation_related_end_time;
    private String reason;
    private java.util.Date vacation_request_time;
    private String reason_for_rejection;

}
