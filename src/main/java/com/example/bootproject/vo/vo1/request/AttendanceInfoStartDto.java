package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


//출근
@Data
@NoArgsConstructor
public class AttendanceInfoStartDto {

    private String employeeId;

    private LocalDate attendanceDate;


    private LocalDateTime startTime;


}
