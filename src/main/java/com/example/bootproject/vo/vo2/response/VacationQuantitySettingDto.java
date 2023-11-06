package com.example.bootproject.vo.vo2.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VacationQuantitySettingDto {
    private int settingKey; //대리키

    private int freshman; //1년 미만 - 개수

    private int senior; //1년 이상 - 개수

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime settingTime; // 설정 시간

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate; // 적용 날짜

    private String employeeId; //사원-사원번호
}
