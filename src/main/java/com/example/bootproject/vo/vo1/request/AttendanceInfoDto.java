package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
public class AttendanceInfoDto {
    private String attendance_info_id; //근태상태표현저장

    private String attendance_status_category;//상태지정
    private String employee_id;//사원번호

    private Time start_time;//출근시간

    private Time end_time;//퇴근시간

    private Date attendance_date;//날짜

}

