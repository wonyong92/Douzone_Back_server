package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

//근태정보
@Data
@NoArgsConstructor
public class AttendanceInfoDto {

    private Long attendanceInfoId;

    private String attendanceStatusCategory;//상태지정

    private String employeeId;//사원번호

    private Time startTime;//출근시간

    private Time endTime;//퇴근시간

    private Date attendanceDate;//날짜

}

