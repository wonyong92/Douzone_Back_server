package com.example.bootproject.vo.vo1.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


//출근
@Data
@AllArgsConstructor
public class AttendanceInfoStartRequestDto {
    @JsonIgnore
    private String employeeId;
    private LocalDate attendanceDate;
    private LocalDateTime startTime;
}
